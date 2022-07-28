package home.bot.service.events.impl.mm.admin;

import static home.bot.dto.enums.MessageHandlerType.ADD_USER_TO_GROUP_MATTERMOST_START;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostAddUserToGroupStartEventHandler")
public class AddUserToGroupStartEventHandler extends AbstractEventHandler<PressMattermostButtonRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService;

  public AddUserToGroupStartEventHandler(MattermostService mattermostService,
                              MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService) {
    this.mattermostService = mattermostService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<PressMattermostButtonRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Add user to group start: {}", eventDto);
    PressMattermostButtonRequest event = eventDto.getEvent();
    processEvent(event);
    return null;
  }

  private void processEvent(PressMattermostButtonRequest event) {
    EventMessage<PressMattermostButtonRequest> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(event.getTriggerId());
    message.setText(ADD_USER_TO_GROUP_MATTERMOST_START.getCommand());
    messageHandlerService.processMessage(message);
  }

}
