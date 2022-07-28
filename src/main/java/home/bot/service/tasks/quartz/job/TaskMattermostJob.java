package home.bot.service.tasks.quartz.job;

import static home.bot.dto.enums.TaskPeriod.ONE_TIME;
import static home.bot.service.impl.TaskServiceImpl.TASK_ID_KEY;

import home.bot.dto.enums.TaskStatus;
import home.bot.entity.Task;
import home.bot.service.TaskService;
import home.bot.utils.ContextUtils;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "QUARTZ_JOB")
public class TaskMattermostJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    LOGGER.info("Start execute mattermost notification job.");
    JobDataMap map = context.getJobDetail().getJobDataMap();
    Long taskId = (Long) map.get(TASK_ID_KEY);
    Task task = taskService().getTaskForJob(taskId);
    taskService().executeTask(task);

    if (ONE_TIME.equals(task.getPeriod())) {
      taskService().updateTaskStatus(taskId, TaskStatus.DONE);
    }
  }

  private static TaskService taskService;
  private TaskService taskService() {
    if (Objects.isNull(taskService)) {
      taskService = ContextUtils.getBean(TaskService.class);
    }
    return taskService;
  }
}
