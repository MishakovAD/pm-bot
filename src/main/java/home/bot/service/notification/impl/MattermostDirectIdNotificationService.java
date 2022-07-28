package home.bot.service.notification.impl;

import home.bot.dto.Message;
import home.bot.entity.NotificationAddress;
import home.bot.service.NotificationAddressService;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.notification.AbstractNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("mattermostDirectIdNotifyHandler")
@Slf4j(topic = "NOTIFICATION_SERVICE.MATTERMOST")
public class MattermostDirectIdNotificationService extends AbstractNotificationService {

  private final MattermostService mattermostService;

  public MattermostDirectIdNotificationService(NotificationAddressService notificationAddressService,
      MattermostService mattermostService) {
    super(notificationAddressService);
    this.mattermostService = mattermostService;
  }

  @Override
  public void notify(Message message) {
    LOGGER.info("Send mattermost message to user: {}", message);
    NotificationAddress address = notificationAddressService.getAddressById(message.getAddressId());
    mattermostService.sendDirectMessageToUser(address.getAddress(), message.getMessage());
  }
}
