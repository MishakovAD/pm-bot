package home.bot.service.tasks.quartz.trigger.impl;

import static org.quartz.CronScheduleBuilder.atHourAndMinuteOnGivenDaysOfWeek;

import home.bot.service.tasks.quartz.trigger.TriggerCreatorService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DateBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@Service("everyWednesdayTrigger")
@Slf4j(topic = "TRIGGER_CREATOR")
public class WednesdayRepeatTriggerCreator implements TriggerCreatorService {

  private static final String TRIGGER_GROUP = "WednesdayTrigger";

  @Override
  public Trigger createTrigger(String triggerName, LocalDate startDate, LocalTime startTime) {
    LOGGER.info("Start create wednesday repeat trigger. Execution date-time: {} : {}", startDate, startTime);
    return TriggerBuilder.newTrigger()
        .withIdentity(triggerName, TRIGGER_GROUP)
        .withSchedule(atHourAndMinuteOnGivenDaysOfWeek(startTime.getHour(), startTime.getMinute(), DateBuilder.WEDNESDAY))
        .build();
  }
}
