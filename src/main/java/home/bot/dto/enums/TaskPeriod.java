package home.bot.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskPeriod {
  ONE_TIME("Не повторяется"),
//  EVERY_DAY("Каждый день"), //todo: после MVP, если понадобится
  EVERY_WORK_DAY("Каждый рабочий день (пн-пт)"),
  EVERY_WEEK("Раз в неделю"),
  EVERY_MONTH("Раз в месяц"),
  EVERY_YEAR("Раз в год");

  private final String description;
}
