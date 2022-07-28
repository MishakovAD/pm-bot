package home.bot.config;

import lombok.SneakyThrows;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

  @Bean
  public SchedulerFactory schedulerFactory() {
    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    return schedulerFactory;
  }

  @Bean
  @SneakyThrows
  public Scheduler scheduler(SchedulerFactory schedulerFactory) {
    return schedulerFactory.getScheduler();
  }

}
