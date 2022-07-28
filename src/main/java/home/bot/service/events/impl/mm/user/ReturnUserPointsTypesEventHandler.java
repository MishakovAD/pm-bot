package home.bot.service.events.impl.mm.user;

import static home.bot.dto.enums.MessageHandlerType.RETURN_USER_POINTS_TYPES;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostGetUserPointsTypesEventHandler")
public class ReturnUserPointsTypesEventHandler extends AbstractEventHandler<PressMattermostButtonRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService;

  public ReturnUserPointsTypesEventHandler(MattermostService mattermostService,
                              MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService) {
    this.mattermostService = mattermostService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<PressMattermostButtonRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Return user points types: {}", eventDto);
    PressMattermostButtonRequest event = eventDto.getEvent();
    processEvent(event);
    return null;
  }

  private void processEvent(PressMattermostButtonRequest event) {
    EventMessage<PressMattermostButtonRequest> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(event.getUserId());
    message.setText(RETURN_USER_POINTS_TYPES.getCommand());
    messageHandlerService.processMessage(message);
  }

}
