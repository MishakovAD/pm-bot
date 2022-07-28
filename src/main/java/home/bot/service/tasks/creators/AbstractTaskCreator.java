package home.bot.service.tasks.creators;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.dto.CreateTaskDto;
import home.bot.dto.enums.TaskStatus;
import home.bot.entity.Task;
import home.bot.entity.User;
import home.bot.repository.TaskRepository;
import home.bot.service.NotificationAddressService;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractTaskCreator implements TaskCreator {

  protected final ObjectMapper objectMapper;
  protected final TaskRepository taskRepository;
  private final NotificationAddressService notificationAddressService;

  protected Task create(User user, CreateTaskDto dto) {
    Task task = new Task();
    task.setUser(user);
    task.setName(dto.getName());
    task.setStatus(TaskStatus.NEW);
    task.setType(dto.getType());
    task.setPeriod(dto.getPeriod());
    task.setExecutionDate(dto.getExecutionDate());
    task.setExecutionTime(dto.getExecutionTime());
    return task;
  }

  protected Long extractNotifyAddress(User user, CreateTaskDto dto) {
    if (Objects.nonNull(dto.getGroupIdForNotify())) {
      return notificationAddressService.getAddressByGroup(dto.getGroupIdForNotify(), dto.getSource()).getId();
    }
    return notificationAddressService.getAddressByUser(user.getId(), dto.getSource()).getId();
  }

}
