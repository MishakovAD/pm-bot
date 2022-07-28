package home.bot.service;

import home.bot.dto.Message;
import home.bot.dto.enums.NotificationSource;

public interface UserNotificationService {
  void userTelegramNotify(Long userId, Message message);

  void notifyByTask(NotificationSource source, Message message);
}
