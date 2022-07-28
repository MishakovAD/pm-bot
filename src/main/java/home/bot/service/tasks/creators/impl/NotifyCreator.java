package home.bot.service.tasks.creators.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.dto.CreateTaskDto;
import home.bot.entity.Task;
import home.bot.entity.User;
import home.bot.repository.TaskRepository;
import home.bot.service.NotificationAddressService;
import home.bot.service.tasks.converters.dto.TaskDto;
import home.bot.service.tasks.creators.AbstractTaskCreator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("notifyCreator")
@Slf4j(topic = "TASK_CREATOR.NOTIFY")
public class NotifyCreator extends AbstractTaskCreator {


  public NotifyCreator(ObjectMapper objectMapper, TaskRepository taskRepository,
      NotificationAddressService notificationAddressService) {
    super(objectMapper, taskRepository, notificationAddressService);
  }

  @Override
  @SneakyThrows
  public Task createTask(User user, CreateTaskDto taskDto) {
    LOGGER.info("Start create NOTIFY task: {}", taskDto);
    Task task = create(user, taskDto);
    TaskDto body = TaskDto.builder()
        .addressId(extractNotifyAddress(user, taskDto))
        .message(taskDto.getMessage())
        .source(taskDto.getSource())
        .build();
    task.setBody(objectMapper.writeValueAsBytes(body));
    return taskRepository.save(task);
  }
}
