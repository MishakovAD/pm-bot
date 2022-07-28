package home.bot.dto.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum KeyboardByActivity {
  CALENDAR_KEYBOARD("tgUserFullName", "calendarKeyboardHandler"),
  POSITION_KEYBOARD("tgUserBirth", "positionKeyboardHandler");

  private final String activityId;
  private final String keyboardHandler;

  private static final Set<KeyboardByActivity> CACHE = Stream.of(KeyboardByActivity.values())
      .collect(Collectors.toSet());

  public static KeyboardByActivity receiveKeyboardByActivity(String activityId) {
    return CACHE.stream()
        .filter(activity -> StringUtils.equals(activityId, activity.getActivityId()))
        .findFirst()
        .orElse(null);
  }
}
