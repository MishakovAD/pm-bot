package home.bot.service.tasks.executors.impl;

import home.bot.dto.Message;
import home.bot.dto.enums.NotificationSource;
import home.bot.entity.Task;
import home.bot.service.UserNotificationService;
import home.bot.service.tasks.converters.BodyConverter;
import home.bot.service.tasks.converters.dto.BirthdayTaskDto;
import home.bot.service.tasks.converters.dto.TaskDto;
import home.bot.service.tasks.executors.AbstractTaskExecutor;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("birthdayReminder")
@Slf4j(topic = "TASK_EXECUTOR.BIRTH_REMINDER")
public class BirthNotifyExecutor extends AbstractTaskExecutor {

  public BirthNotifyExecutor(UserNotificationService userNotificationService,
                              Map<String, BodyConverter<?>> bodyConverters) {
    super(userNotificationService, bodyConverters);
  }

  @Override
  public void execute(Task task) {
    LOGGER.info("Start execute birthday notify task. Task: {}", task);
    BirthdayTaskDto bodyDto = getTaskBodyDto(task);
    Long userId = bodyDto.getUserIdToRemind();
    NotificationSource source = bodyDto.getSource();
//    userNotificationService.notifyByTask(source, new Message(, bodyDto.getMessage()));
  }
}
