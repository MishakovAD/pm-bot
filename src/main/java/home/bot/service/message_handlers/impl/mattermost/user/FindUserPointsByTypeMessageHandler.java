package home.bot.service.message_handlers.impl.mattermost.user;

import static home.bot.dto.enums.ContextMapValuesBotMenuButtons.CONTEXT_MAP_KEY;
import static home.bot.dto.enums.ContextMapValuesBotMenuButtons.POINTS_TYPE_MAP;
import static home.bot.dto.enums.MattermostBotMenuButton.POINTS_ACTIONS_USER_BUTTONS;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.enums.PointType;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.PointsService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.bis5.mattermost.model.Post;
import net.bis5.mattermost.model.SlackAttachment;
import org.springframework.stereotype.Service;

@Service("findUserPointsMattermostMessage")
@Slf4j(topic = "MESSAGE_HANDLER.USER_POINTS_MATTERMOST")
public class FindUserPointsByTypeMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  private static final String TITLE_MESSAGE = "## **Твои открытые вопросы**\n";

  private final PointsService pointsService;

  public FindUserPointsByTypeMessageHandler(PointsService pointsService,
      MattermostService mattermostService,
      InteractiveMattermostClient mattermostClient,
      MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.pointsService = pointsService;
  }

  @Override
  public EventMessageResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    PressMattermostButtonRequest request = message.getMessage();
    Map<String, String> context = request.getContext();
    String pointsType = context.get(CONTEXT_MAP_KEY);
    PointType pointType = POINTS_TYPE_MAP.get(pointsType);

    String postId = request.getPostId();
    String channelId = request.getChannelId(); //== directId (так как вызывается из личных сообщений)
    mattermostService.editPost(editMenuPost(postId, channelId));

    String messageToPost = pointsService.createMessageForOpenUserQuestions(channelId, pointType, TITLE_MESSAGE);
    mattermostClient.createPost(createPostForUserQuestions(messageToPost, channelId));
    return null;
  }

  private Post createPostForUserQuestions(String message, String channelId) {
    Post post = new Post();
    post.setChannelId(channelId);
    List<SlackAttachment> attachments = POINTS_ACTIONS_USER_BUTTONS.stream()
        .map(mattermostElementBuilder::createAttachment)
        .toList();
    post.setProps(Map.of("attachments", attachments));
    post.setMessage(message);
    return post;
  }

  private Post editMenuPost(String postId, String channelId) {
    Post post = new Post(channelId, "Получите-распишитесь. Выбирай нужные действия и вперед!");
    post.setId(postId);
    return post;
  }
}
