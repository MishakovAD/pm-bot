package home.bot.service.events.impl.mm.tasks;

import static home.bot.dto.enums.MessageHandlerType.REMOVE_USER_TASK_MATTERMOST_START;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import lombok.extern.slf4j.Slf4j;
import net.bis5.mattermost.model.Post;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostUserTaskRemoveStartEventHandler")
public class TaskRemoveStartEventHandler extends AbstractEventHandler<PressMattermostButtonRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService;

  public TaskRemoveStartEventHandler(MattermostService mattermostService,
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
    EventMessage<PressMattermostButtonRequest> message = buildEventMessage(event, event.getTriggerId(), REMOVE_USER_TASK_MATTERMOST_START.getCommand());
    messageHandlerService.processMessage(message);

    Post post = new Post(event.getChannelId(), "Готово!");
    post.setId(event.getPostId());
    mattermostService.editPost(post);

  }

}
