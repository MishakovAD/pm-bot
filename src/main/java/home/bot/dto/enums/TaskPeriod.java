package home.bot.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskPeriod {
  ONE_TIME("Не повторяется", "oneTimeTrigger"),
//  EVERY_DAY("Каждый день", ""), //todo: после MVP, если понадобится
  EVERY_WORK_DAY("Каждый рабочий день (пн-пт)", "workDaysTrigger"),
  EVERY_WEEK("Раз в неделю", "everyWeekTrigger"),
  EVERY_MONTH("Раз в месяц", "everyMonthTrigger"),
  EVERY_YEAR("Раз в год", "everyYearTrigger");

  private final String description;
  private final String triggerCreatorName;
}
