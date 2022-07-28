package home.bot.service.message_handlers.impl.mattermost.admin;

import static home.bot.dto.enums.MattermostDialogElementValue.MM_CREATE_GROUP_MAPPING;
import static home.bot.dto.enums.MattermostDialogElementValue.MM_USER_REGISTRATION_MAPPING;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.CreateGroupDto;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.service.PointsService;
import home.bot.service.UserGroupService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("finishCreateGroupMessage")
@Slf4j(topic = "MESSAGE_HANDLER.CREATE_GROUP_MATTERMOST")
public class CreateGroupFinishMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<BaseMattermostSubmitDialogRequest, Y> {

  private final UserGroupService userGroupService;

  public CreateGroupFinishMessageHandler(MattermostService mattermostService,
      InteractiveMattermostClient mattermostClient,
      MattermostElementBuilder mattermostElementBuilder,
      UserGroupService userGroupService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.userGroupService = userGroupService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<BaseMattermostSubmitDialogRequest> message) {
    LOGGER.info("Start process message.");
    BaseMattermostSubmitDialogRequest request = message.getMessage();
    Map<String, Object> submission = request.getSubmission();
    CreateGroupDto dto = new CreateGroupDto();
    submission.entrySet()
        .stream()
        .filter(entry -> Objects.nonNull(entry.getValue()))
        .forEach(entry -> {
          MM_CREATE_GROUP_MAPPING.get(entry.getKey()).accept(dto, String.valueOf(entry.getValue()));
        });
    userGroupService.createGroup(dto, request.getUserId());
    return null;
  }
}
