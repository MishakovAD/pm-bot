package home.bot.service.tasks.executors.impl;

import home.bot.dto.Message;
import home.bot.entity.Task;
import home.bot.service.UserNotificationService;
import home.bot.service.tasks.converters.BodyConverter;
import home.bot.service.tasks.converters.dto.TaskDto;
import home.bot.service.tasks.executors.AbstractTaskExecutor;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("notify")
@Slf4j(topic = "TASK_EXECUTOR.NOTIFY")
public class NotifyExecutor extends AbstractTaskExecutor {

  public NotifyExecutor(Map<String, BodyConverter<?>> bodyConverters,
                        UserNotificationService userNotificationService) {
    super(userNotificationService, bodyConverters);
  }

  @Override
  public void execute(Task task) {
    LOGGER.info("Start execute notify task. Task id: {}", task.getId());
    TaskDto body = getTaskBodyDto(task);
    userNotificationService.notifyByTask(body.getSource(), new Message(body.getAddressId(), body.getMessage()));
  }
}
