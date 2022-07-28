package home.bot.service.tasks.executors.impl;

import home.bot.entity.Task;
import home.bot.service.tasks.executors.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("do_something")
@Slf4j(topic = "TASK_EXECUTOR.DO_SOMETHING")
public class DoSomethingExecutor implements TaskExecutor {

  @Override
  public void execute(Task task) {
    LOGGER.info("Start execute anything task. Task: {}", task);
  }
}
