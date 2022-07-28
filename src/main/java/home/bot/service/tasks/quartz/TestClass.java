package home.bot.service.tasks.quartz;

import static org.quartz.CronScheduleBuilder.atHourAndMinuteOnGivenDaysOfWeek;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

import java.time.LocalDateTime;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "QUARTZ")
@RequiredArgsConstructor
public class TestClass {

  private final Scheduler scheduler;

  @SneakyThrows
//  @PostConstruct
  public void init() {
    JobDetail jobDetail = JobBuilder.newJob()
        .ofType(SimpleJob.class)
        .withIdentity("qwewef", "wer")
        .build();
    Trigger trigger = TriggerBuilder.newTrigger()
        .withIdentity("myTrigger1", "group1") //must
        .startNow()
//        .withSchedule(atHourAndMinuteOnGivenDaysOfWeek(17, 0, DateBuilder.MONDAY, DateBuilder.TUESDAY, DateBuilder.WEDNESDAY, DateBuilder.THURSDAY, DateBuilder.FRIDAY))
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(40)
            .repeatForever())
        .build();
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
  }

}
