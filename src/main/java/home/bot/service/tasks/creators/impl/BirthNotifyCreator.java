package home.bot.service.tasks.creators.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.dto.CreateBirthTaskDto;
import home.bot.dto.CreateTaskDto;
import home.bot.dto.enums.TaskPeriod;
import home.bot.dto.enums.TaskStatus;
import home.bot.dto.enums.TaskType;
import home.bot.entity.Task;
import home.bot.entity.User;
import home.bot.repository.TaskRepository;
import home.bot.service.NotificationAddressService;
import home.bot.service.tasks.converters.dto.BirthdayTaskDto;
import home.bot.service.tasks.creators.AbstractTaskCreator;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("birthNotifyCreator")
@Slf4j(topic = "TASK_CREATOR.BIRTH")
public class BirthNotifyCreator extends AbstractTaskCreator {

  private static final String BIRTH_NOTIFY_MESSAGE = "Через 3 дня у пользователя %s День Рождения! Не забудь поздравить! (:";

  public BirthNotifyCreator(ObjectMapper objectMapper, TaskRepository taskRepository,
      NotificationAddressService notificationAddressService) {
    super(objectMapper, taskRepository, notificationAddressService);
  }


  @Override
  @SneakyThrows
  public Task createTask(User user, CreateTaskDto taskDto) {
    LOGGER.info("Start create BIRTHDAY_REMINDER task: {}", taskDto);
    Task task = create(user, taskDto);
    task.setType(TaskType.BIRTHDAY_REMINDER);
    task.setPeriod(TaskPeriod.EVERY_YEAR);
    BirthdayTaskDto birthdayTaskDto = BirthdayTaskDto.builder()
        .userIdToRemind(user.getId())
        .addressId(extractNotifyAddress(user, taskDto))
        .source(taskDto.getSource())
        .message(String.format(BIRTH_NOTIFY_MESSAGE, ((CreateBirthTaskDto) taskDto).getBirthUserName()))
        .build();
    task.setBody(objectMapper.writeValueAsBytes(birthdayTaskDto));
    return taskRepository.save(task);
  }
}
