package home.bot.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {
  DAILY_TYPE("Daily"),
  PERSONAL_TYPE("Личные");

  private final String type;
}
