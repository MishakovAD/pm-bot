package home.bot.service.tasks.quartz.trigger;

import java.time.LocalDate;
import java.time.LocalTime;
import org.quartz.Trigger;

public interface TriggerCreatorService {
  Trigger createTrigger(String triggerName, LocalDate startDate, LocalTime startTime);
}
