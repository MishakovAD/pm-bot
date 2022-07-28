package home.bot.service.message_handlers.impl.mattermost.tasks;

import static home.bot.dto.enums.MattermostDialogElementValue.MM_USER_POINTS_CREATE;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.UserPointDto;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.service.TaskService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("finishMattermostRemoveUserTaskMessage")
@Slf4j(topic = "MESSAGE_HANDLER.TASKS")
public class RemoveUserTaskFinishMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<BaseMattermostSubmitDialogRequest, Y> {

  private final TaskService taskService;

  public RemoveUserTaskFinishMessageHandler(MattermostService mattermostService,
      InteractiveMattermostClient mattermostClient,
      MattermostElementBuilder mattermostElementBuilder,
      TaskService taskService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.taskService = taskService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<BaseMattermostSubmitDialogRequest> message) {
    LOGGER.info("Start process message.");
    //todo: реализовать
    BaseMattermostSubmitDialogRequest request = message.getMessage();
    Map<String, Object> fields = request.getSubmission();
    return null;
  }
}
