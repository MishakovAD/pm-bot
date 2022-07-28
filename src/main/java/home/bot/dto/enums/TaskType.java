package home.bot.dto.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum TaskType {
  NOTIFY("notify", "notifyBodyConverter", "notifyCreator", "Отложенное сообщение"),
  BIRTHDAY_REMINDER("birthdayReminder", "birthdayReminderBodyConverter", "birthNotifyCreator", "Напоминание о дне рождения"),
  MEETING_REMINDER("meetingReminder", "notifyBodyConverter", "meetingNotifyCreator", "Напоминание о daily + список открытых вопросов"),
  TODO_LIST_NOTIFY("todoListNotify", "todoListNotifyBodyConverter", "", "TODO list (what is it)"),
  POKER_PLANING("pokerPlaning", "pokerPlaningBodyConverter", "", "Покер-планирование"),
  ASK_ABOUT_PROGRESS("askAboutProgress", "askAboutProgressBodyConverter", "", "Опрос по открытым вопросам")
  ;

  private final String type;
  private final String bodyConverter;
  private final String taskCreator;
  private final String description;

  private static final Set<TaskType> CACHE = Stream.of(TaskType.values())
      .collect(Collectors.toSet());

  public static TaskType receiveTaskType(String value) {
    return CACHE.stream()
        .filter(val -> StringUtils.equals(value, val.getType()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Unknown task: %s", value)));
  }
}
