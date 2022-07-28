package home.bot.service.tasks.creators;

import home.bot.dto.CreateTaskDto;
import home.bot.entity.Task;
import home.bot.entity.User;

public interface TaskCreator {
  Task createTask(User user, CreateTaskDto taskDto);
}
