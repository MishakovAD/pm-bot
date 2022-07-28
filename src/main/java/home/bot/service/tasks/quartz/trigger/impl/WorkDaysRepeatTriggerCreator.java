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

@Service("workDaysTrigger")
@Slf4j(topic = "TRIGGER_CREATOR")
public class WorkDaysRepeatTriggerCreator implements TriggerCreatorService {

  private static final String TRIGGER_GROUP = "WorkDaysTrigger";

  @Override
  public Trigger createTrigger(String triggerName, LocalDate startDate, LocalTime startTime) {
    LOGGER.info("Start create work days repeat trigger. Execution date-time: {} : {}", startDate, startTime);
    return TriggerBuilder.newTrigger()
        .withIdentity(triggerName, TRIGGER_GROUP)
        .withSchedule(atHourAndMinuteOnGivenDaysOfWeek(startTime.getHour(), startTime.getMinute(), DateBuilder.MONDAY, DateBuilder.TUESDAY, DateBuilder.WEDNESDAY, DateBuilder.THURSDAY, DateBuilder.FRIDAY))
        .build();
  }
}
