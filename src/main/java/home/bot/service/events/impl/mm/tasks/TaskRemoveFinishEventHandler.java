package home.bot.service.events.impl.mm.tasks;

import static home.bot.dto.enums.MessageHandlerType.REMOVE_USER_TASK_MATTERMOST_FINISH;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostUserTaskRemoveFinishEventHandler")
public class TaskRemoveFinishEventHandler extends AbstractEventHandler<BaseMattermostSubmitDialogRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<BaseMattermostSubmitDialogRequest, ?> messageHandlerService;

  public TaskRemoveFinishEventHandler(MattermostService mattermostService,
                              MessageHandlerService<BaseMattermostSubmitDialogRequest, ?> messageHandlerService) {
    this.mattermostService = mattermostService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<BaseMattermostSubmitDialogRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Event user points create: {}", eventDto);
    BaseMattermostSubmitDialogRequest event = eventDto.getEvent();
    if (event.isCancelled()) {
      LOGGER.info("Cancelled");
      return null;
    }
    processEvent(event);
    return null;
  }

  private void processEvent(BaseMattermostSubmitDialogRequest event) {
    EventMessage<BaseMattermostSubmitDialogRequest> message = buildEventMessage(event, event.getChannelId(), REMOVE_USER_TASK_MATTERMOST_FINISH.getCommand());
    messageHandlerService.processMessage(message);
  }

}
