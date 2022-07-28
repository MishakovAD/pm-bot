package home.bot.service.tasks.executors;

import home.bot.dto.enums.TaskType;
import home.bot.entity.Task;
import home.bot.exception.UnsupportedTaskType;
import home.bot.service.UserNotificationService;
import home.bot.service.tasks.converters.BodyConverter;
import home.bot.service.tasks.converters.dto.TaskDto;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "TASK_EXECUTOR.ABSTRACT")
public abstract class AbstractTaskExecutor implements TaskExecutor {

  protected final UserNotificationService userNotificationService;
  private final Map<String, BodyConverter<?>> bodyConverters;

  protected <T> T getTaskBodyDto(Task task) {
    return (T) getBodyConverter(task.getType())
        .convert(task.getBody());
  }

  protected BodyConverter<?> getBodyConverter(TaskType type) {
    return Optional.ofNullable(bodyConverters.get(type.getBodyConverter()))
        .orElseThrow(() -> new UnsupportedTaskType(String.format("Unsupported task type for converter: %s", type.getBodyConverter())));
  }

}
