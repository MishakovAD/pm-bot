package home.bot.service.events.impl.mm.other;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.matermost.request.SlashCommandRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostSlashCommandEventHandler")
public class MattermostSlashCommandEventHandler extends AbstractEventHandler<SlashCommandRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<SlashCommandRequest, ?> messageHandlerService;

  public MattermostSlashCommandEventHandler(MattermostService mattermostService,
                              MessageHandlerService<SlashCommandRequest, ?> messageHandlerService) {
    this.mattermostService = mattermostService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<SlashCommandRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Event slash command: {}", eventDto);
    SlashCommandRequest event = eventDto.getEvent();
    processEvent(event);
    return null;
  }

  private void processEvent(SlashCommandRequest event) {
    EventMessage<SlashCommandRequest> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(event.getTriggerId());
    message.setText(event.getCommand());
    EventMessageResponse response = messageHandlerService.processMessage(message);
  }

}
