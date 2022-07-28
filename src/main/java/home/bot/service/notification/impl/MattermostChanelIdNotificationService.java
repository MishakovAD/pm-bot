package home.bot.service.notification.impl;

import home.bot.dto.Message;
import home.bot.entity.NotificationAddress;
import home.bot.repository.NotificationAddressRepository;
import home.bot.service.NotificationAddressService;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.notification.AbstractNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("mattermostChanelIdNotifyHandler")
@Slf4j(topic = "NOTIFICATION_SERVICE.MATTERMOST")
public class MattermostChanelIdNotificationService extends AbstractNotificationService {

  private final MattermostService mattermostService;

  public MattermostChanelIdNotificationService(NotificationAddressService notificationAddressService,
      MattermostService mattermostService) {
    super(notificationAddressService);
    this.mattermostService = mattermostService;
  }

  @Override
  public void notify(Message message) {
    LOGGER.info("Send mattermost message to chanel: {}", message);
    NotificationAddress address = notificationAddressService.getAddressById(message.getAddressId());
    mattermostService.sendMessageFromBotToGroup(address.getAddress(), message.getMessage());
  }
}
