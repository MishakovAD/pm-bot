package home.bot.service.impl;

import home.bot.dto.CreateTaskDto;
import home.bot.dto.enums.TaskPeriod;
import home.bot.dto.enums.TaskStatus;
import home.bot.dto.enums.TaskType;
import home.bot.entity.Task;
import home.bot.entity.User;
import home.bot.exception.UnsupportedTaskType;
import home.bot.repository.TaskRepository;
import home.bot.repository.UserRepository;
import home.bot.service.TaskService;
import home.bot.service.tasks.creators.TaskCreator;
import home.bot.service.tasks.executors.TaskExecutor;
import home.bot.service.tasks.quartz.job.TaskMattermostJob;
import home.bot.service.tasks.quartz.trigger.TriggerCreatorService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class TaskServiceImpl implements TaskService {

  public static final String TASK_ID_KEY = "taskId";
  public static final String TASK_GROUP = "UserTask";

  private final Scheduler scheduler;
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final Map<String, TaskCreator> taskCreators;
  private final Map<String, TaskExecutor> taskExecutors;
  private final Map<String, TriggerCreatorService> triggerCreators;

  @Override
  public Task getTaskForJob(Long taskId) {
    return taskRepository.findByIdAndStatus(taskId, TaskStatus.IN_PROGRESS)
        .orElseThrow(() -> new RuntimeException(String.format("Task with id: %s and status IN_PROGRESS not found!", taskId)));
  }

  @Override
  public List<Task> getTodayTaskList() {
    return taskRepository.findAllByExecutionDateAndStatus(LocalDate.now(), TaskStatus.NEW);
  }

  @Override
  @SneakyThrows
  @Transactional
  public void scheduleQuartzTaskByEntity(Task task) {
    Trigger trigger = createQuartzTrigger(task.getPeriod(), task.getExecutionDate(), task.getExecutionTime());
    JobDataMap map = new JobDataMap();
    map.put(TASK_ID_KEY, task.getId());
    JobDetail jobDetail = JobBuilder.newJob()
        .ofType(TaskMattermostJob.class)
        .setJobData(map)
        .withIdentity(task.getName(), TASK_GROUP)
        .build();
    scheduler.scheduleJob(jobDetail, trigger);
    updateTaskStatus(task.getId(), TaskStatus.IN_PROGRESS);
  }

  @Override
  @Transactional
  public void updateTaskStatus(Long taskId, TaskStatus status) {
    Task task = taskRepository.getById(taskId);
    task.setStatus(status);
    taskRepository.save(task);
  }

  @Override
  public Task createTask(Long userId, CreateTaskDto createTaskDto) {
    User user = userRepository.getById(userId);
    TaskType type = createTaskDto.getType();
    Task task = taskCreators.get(type.getTaskCreator())
        .createTask(user, createTaskDto);
    scheduleQuartzTaskByEntity(task);
    return task;
  }

  @Override
  public void executeTask(Task task) {
    getTaskExecutor(task.getType())
        .execute(task);
  }

  protected TaskExecutor getTaskExecutor(TaskType type) {
    return Optional.ofNullable(taskExecutors.get(type.getType()))
        .orElseThrow(() -> new UnsupportedTaskType(String.format("Unsupported task type for taskExecutor: %s", type.getType())));
  }

  private Trigger createQuartzTrigger(TaskPeriod period, LocalDate executionDate, LocalTime executionTime) {
    return triggerCreators.get(period.getTriggerCreatorName()).createTrigger(, executionDate, executionTime);
  }

//  @Override //выполнение скрипта + очистка quartz tables
//  @Transactional
//  public void clearQuartzTables() {
//    LOGGER.info("Start clear quartz tables.");
//    Query query = entityManager.createNativeQuery(CLEAR_QUARTZ_TABLES_SCRIPT);
//    query.executeUpdate();
//  }
//
//  private final static String CLEAR_QUARTZ_TABLES_SCRIPT = "delete from QRTZ_CRON_TRIGGERS;"
//                                                          + "delete from QRTZ_SIMPLE_TRIGGERS;"
//                                                          + "delete from QRTZ_TRIGGERS;"
//                                                          + "delete from QRTZ_JOB_DETAILS;"
//                                                          + "delete from QRTZ_FIRED_TRIGGERS;"
//                                                          + "delete from QRTZ_LOCKS;"
//                                                          + "delete from QRTZ_SCHEDULER_STATE;"
//      ;
//
//  @PersistenceContext
//  protected EntityManager entityManager;
}
