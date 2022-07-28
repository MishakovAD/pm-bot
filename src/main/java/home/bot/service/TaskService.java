package home.bot.service;

import home.bot.dto.CreateTaskDto;
import home.bot.dto.enums.TaskStatus;
import home.bot.entity.Task;
import java.util.List;

public interface TaskService {

  Task getTaskForJob(Long taskId);
  List<Task> getTodayTaskList();
  void scheduleQuartzTaskByEntity(Task task);

  void updateTaskStatus(Long taskId, TaskStatus status);

  Task createTask(Long userId, CreateTaskDto createTaskDto);

  void executeTask(Task task);
}
