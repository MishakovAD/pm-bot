package home.bot.service.tasks.creators.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.dto.CreateTaskDto;
import home.bot.entity.Task;
import home.bot.entity.User;
import home.bot.repository.TaskRepository;
import home.bot.service.NotificationAddressService;
import home.bot.service.tasks.converters.dto.TaskDto;
import home.bot.service.tasks.creators.AbstractTaskCreator;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("meetingNotifyCreator")
@Slf4j(topic = "TASK_CREATOR.MEETING")
public class MeetingNotifyCreator extends AbstractTaskCreator {


  public MeetingNotifyCreator(ObjectMapper objectMapper, TaskRepository taskRepository,
      NotificationAddressService notificationAddressService) {
    super(objectMapper, taskRepository, notificationAddressService);
  }

  @Override
  @SneakyThrows
  public Task createTask(User user, CreateTaskDto taskDto) {
    LOGGER.info("Start create MEETING_NOTIFY task: {}", taskDto);
    Task task = create(user, taskDto);
    TaskDto body = TaskDto.builder()
        .addressId(extractNotifyAddress(user, taskDto))
        .message(StringUtils.EMPTY) //в выполнении получать список открытых вопросов
        .source(taskDto.getSource())
        .build();
    task.setBody(objectMapper.writeValueAsBytes(body));
    return taskRepository.save(task);
  }
}
