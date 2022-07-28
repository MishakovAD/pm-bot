package home.bot.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationSource {
  TELEGRAM("telegramNotifyHandler"),
  MATTERMOST_USER_ID("mattermostUserIdNotifyHandler"), //уникальный mm userId, по которому можно создать directChannel
  MATTERMOST_DIRECT_ID("mattermostDirectIdNotifyHandler"), //созданный канал для ЛС пользователю
  MATTERMOST_CHANEL_ID("mattermostChanelIdNotifyHandler"), //привязывается к админу группы (т.е. все нотификации группы производятся "через" админа)
  EMAIL("emailNotifyHandler"),
  PHONE("phoneNotifyHandler");

  private final String notificationHandler;
}
