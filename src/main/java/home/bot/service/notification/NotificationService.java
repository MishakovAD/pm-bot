package home.bot.service.notification;

import home.bot.dto.Message;

public interface NotificationService {
  void notify(Message message);
}
