package home.bot.dto.enums;

import static home.bot.dto.enums.PointType.DAILY_TYPE;
import static home.bot.dto.enums.PointType.PERSONAL_TYPE;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContextMapValuesBotMenuButtons {
  DAILY("dailyPoints"),
  PERSONAL("personalPoints");

  private final String value;

  public static final Map<String, PointType> POINTS_TYPE_MAP = Map.of(DAILY.getValue(), DAILY_TYPE,
      PERSONAL.getValue(), PERSONAL_TYPE);

  public static final String CONTEXT_MAP_KEY = "key";
}
