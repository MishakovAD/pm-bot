package home.bot.service.message_handlers.impl.mattermost.tasks;

import static home.bot.dto.enums.MattermostDialogElementValue.MM_CREATE_USER_TASK_MAPPING;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_GROUP_VALUE;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.CreateTaskDto;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.enums.NotificationSource;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.entity.User;
import home.bot.service.TaskService;
import home.bot.service.UserService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("finishMattermostCreateUserTaskMessage")
@Slf4j(topic = "MESSAGE_HANDLER.TASKS")
public class CreateUserTaskFinishMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<BaseMattermostSubmitDialogRequest, Y> {

  private final TaskService taskService;
  private final UserService userService;

  public CreateUserTaskFinishMessageHandler(MattermostService mattermostService,
      InteractiveMattermostClient mattermostClient,
      MattermostElementBuilder mattermostElementBuilder,
      TaskService taskService,
      UserService userService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.taskService = taskService;
    this.userService = userService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<BaseMattermostSubmitDialogRequest> message) {
    LOGGER.info("Start process message.");
    BaseMattermostSubmitDialogRequest request = message.getMessage();
    Map<String, Object> fields = request.getSubmission();
    CreateTaskDto createTaskDto = new CreateTaskDto(extractSource(fields));
    User user = userService.getUserByAddress(request.getUserId());
    fields.entrySet()
        .stream()
        .filter(entry -> Objects.nonNull(entry.getValue()))
        .forEach(entry -> {
          MM_CREATE_USER_TASK_MAPPING.get(entry.getKey()).accept(createTaskDto, String.valueOf(entry.getValue()));
        });
    taskService.createTask(user.getId(), createTaskDto);
    return null;
  }

  private NotificationSource extractSource(Map<String, Object> fields) {
    return Objects.isNull(fields.get(USER_GROUP_VALUE.getName())) ? NotificationSource.MATTERMOST_DIRECT_ID : NotificationSource.MATTERMOST_CHANEL_ID;
  }
}
