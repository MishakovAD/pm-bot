package home.bot.service.tasks;

import home.bot.dto.enums.TaskStatus;
import home.bot.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "TASK_EXECUTOR_SERVICE")
public class TaskExecutorService {

  private static final long MINUTES_10 = 1000*60*10L;

  private final TaskService taskService;

  @SneakyThrows
  @Scheduled(fixedDelay = MINUTES_10) //todo возможно сервис вообще не нужен
  public void scheduleTasks() {
    LOGGER.info("Start scheduler for add new tasks!");
    taskService.getTodayTaskList()
        .forEach(taskService::scheduleQuartzTaskByEntity);
  }
}
