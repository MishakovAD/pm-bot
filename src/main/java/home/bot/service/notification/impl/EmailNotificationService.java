package home.bot.service.notification.impl;

import home.bot.dto.Message;
import home.bot.repository.NotificationAddressRepository;
import home.bot.service.NotificationAddressService;
import home.bot.service.notification.AbstractNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("emailNotifyHandler")
@Slf4j(topic = "NOTIFICATION_SERVICE.EMAIL")
public class EmailNotificationService extends AbstractNotificationService {

  public EmailNotificationService(NotificationAddressService notificationAddressService) {
    super(notificationAddressService);
  }

  @Override
  public void notify(Message message) {
    LOGGER.info("Send e-mail message to user: {}", message);
  }
}
