package home.bot.service.tasks.executors.impl;

import home.bot.dto.Message;
import home.bot.entity.NotificationAddress;
import home.bot.entity.Task;
import home.bot.entity.UserGroup;
import home.bot.service.NotificationAddressService;
import home.bot.service.PointsService;
import home.bot.service.UserGroupService;
import home.bot.service.UserNotificationService;
import home.bot.service.tasks.converters.BodyConverter;
import home.bot.service.tasks.converters.dto.TaskDto;
import home.bot.service.tasks.executors.AbstractTaskExecutor;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("meetingReminder")
@Slf4j(topic = "TASK_EXECUTOR.MEETING")
public class MeetingNotifyExecutor extends AbstractTaskExecutor {

  private final PointsService pointsService;
  private final UserGroupService userGroupService;
  private final NotificationAddressService addressService;

  public MeetingNotifyExecutor(Map<String, BodyConverter<?>> bodyConverters,
      UserNotificationService userNotificationService,
      PointsService pointsService,
      UserGroupService userGroupService,
      NotificationAddressService addressService) {
    super(userNotificationService, bodyConverters);
    this.pointsService = pointsService;
    this.userGroupService = userGroupService;
    this.addressService = addressService;
  }

  @Override
  public void execute(Task task) {
    LOGGER.info("Start execute meeting notify task. Task id: {}", task.getId());
    TaskDto body = getTaskBodyDto(task);
    NotificationAddress groupAddress = addressService.getAddressById(body.getAddressId());
    UserGroup group = userGroupService.getGroupByAddress(groupAddress)
        .orElseThrow(() -> new RuntimeException("Group not found!"));
    String text = pointsService.getTodayDailyPointsText(group.getId());
    userNotificationService.notifyByTask(body.getSource(), new Message(body.getAddressId(), text));
  }
}
