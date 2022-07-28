package home.bot.service.message_handlers.impl.mattermost.user;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("returnUserPointsTypesMattermostMessage")
@Slf4j(topic = "MESSAGE_HANDLER.POINTS_TYPES_MATTERMOST")
public class ReturnUserPointsTypesMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  public ReturnUserPointsTypesMessageHandler(MattermostService mattermostService,
                                                      InteractiveMattermostClient mattermostClient,
                                                      MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
  }

  @Override
  public EventMessageResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    PressMattermostButtonRequest request = message.getMessage();
    String userId = message.getChatId();
    String postId = request.getPostId();
    String channelId = request.getChannelId();
    mattermostService.sendPointsTypesToUser(userId, channelId, postId);
    return null;
  }
}
