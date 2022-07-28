package home.bot.service.message_handlers.impl.mattermost.points;

import static home.bot.dto.enums.MattermostDialogElementValue.MM_USER_POINTS_CREATE;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.UserPointDto;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.service.PointsService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("createMattermostUserPointsMessage")
@Slf4j(topic = "MESSAGE_HANDLER.POINTS")
public class CreateUserPointFinishMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<BaseMattermostSubmitDialogRequest, Y> {

  private final PointsService pointsService;

  public CreateUserPointFinishMessageHandler(MattermostService mattermostService,
      InteractiveMattermostClient mattermostClient,
      MattermostElementBuilder mattermostElementBuilder,
      PointsService pointsService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.pointsService = pointsService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<BaseMattermostSubmitDialogRequest> message) {
    LOGGER.info("Start process message.");
    BaseMattermostSubmitDialogRequest request = message.getMessage();
    Map<String, Object> questions = request.getSubmission();
    UserPointDto userPointDto = new UserPointDto();
    userPointDto.setUserId(request.getUserId());
    questions.entrySet()
        .stream()
        .filter(entry -> Objects.nonNull(entry.getValue()))
        .forEach(entry -> {
          MM_USER_POINTS_CREATE.get(entry.getKey()).accept(userPointDto, String.valueOf(entry.getValue()));
        });
    pointsService.createTodayUserPoint(userPointDto);
    return null;
  }
}
