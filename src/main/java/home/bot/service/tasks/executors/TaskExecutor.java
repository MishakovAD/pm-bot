package home.bot.service.tasks.executors;

import home.bot.entity.Task;

public interface TaskExecutor {
  void execute(Task task);
}
