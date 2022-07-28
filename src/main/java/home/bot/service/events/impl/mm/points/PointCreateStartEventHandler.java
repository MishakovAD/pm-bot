package home.bot.service.events.impl.mm.points;

import static home.bot.dto.enums.MessageHandlerType.CREATE_USER_POINTS_MATTERMOST_START;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import net.bis5.mattermost.model.Post;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostUserPointCreateStartEventHandler")
public class PointCreateStartEventHandler extends AbstractEventHandler<PressMattermostButtonRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService;

  public PointCreateStartEventHandler(MattermostService mattermostService,
                              MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService) {
    this.mattermostService = mattermostService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<PressMattermostButtonRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Event daily points start: {}", eventDto);
    PressMattermostButtonRequest event = eventDto.getEvent();
    processEvent(event);
    return null;
  }

  private void processEvent(PressMattermostButtonRequest event) {
    EventMessage<PressMattermostButtonRequest> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(event.getTriggerId());
    message.setText(CREATE_USER_POINTS_MATTERMOST_START.getCommand());
    messageHandlerService.processMessage(message);

    Map<String, String> context = event.getContext();
    if (Objects.isNull(context) || context.isEmpty()) { //Два варианта: Пользователь создает вопрос или Вопрос добавляется из уже полученного списка (ADD_NEW_QUESTIONS_TO_POINT_BUTTON.context)
      Post post = new Post(event.getChannelId(), "Спасибо! Ты просто супер!"); //todo:  сделать словарь фраз благодарности.
      post.setId(event.getPostId());
      mattermostService.editPost(post);
    }
  }

}
