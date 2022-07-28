package home.bot.service.impl;

import static home.bot.dto.enums.NotificationSource.TELEGRAM;

import home.bot.dto.Message;
import home.bot.dto.enums.NotificationSource;
import home.bot.service.UserNotificationService;
import home.bot.service.notification.NotificationService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class UserNotificationServiceImpl implements UserNotificationService {

  private final Map<String, NotificationService> notificationServices;

  @Override
  public void userTelegramNotify(Long userId, Message message) {
    notificationServices.get(TELEGRAM.getNotificationHandler())
        .notify(message);
  }

  @Override
  public void notifyByTask(NotificationSource source, Message message) {
    notificationServices.get(source.getNotificationHandler())
        .notify(message);
  }
}
