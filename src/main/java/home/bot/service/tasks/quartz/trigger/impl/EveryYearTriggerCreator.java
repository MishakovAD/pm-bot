package home.bot.service.tasks.quartz.trigger.impl;

import home.bot.service.tasks.quartz.trigger.TriggerCreatorService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@Service("everyYearTrigger")
@Slf4j(topic = "TRIGGER_CREATOR")
public class EveryYearTriggerCreator implements TriggerCreatorService {

  private static final String TRIGGER_GROUP = "EveryYearTrigger";

  @Override
  public Trigger createTrigger(String triggerName, LocalDate startDate, LocalTime startTime) {
    LOGGER.info("Start create every year trigger. Execution date-time: {} : {}", startDate, startTime);
    LocalDateTime dateTime = LocalDateTime.of(startDate, startTime);
    return TriggerBuilder.newTrigger()
        .withIdentity(triggerName, TRIGGER_GROUP)
        .startAt(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()))
        .withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
            .withIntervalInMonths(12))
        .build();
  }
}
