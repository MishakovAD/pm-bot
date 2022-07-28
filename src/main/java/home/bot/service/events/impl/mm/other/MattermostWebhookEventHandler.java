package home.bot.service.events.impl.mm.other;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.WebhookRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("MattermostWebhookEventHandler")
@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
public class MattermostWebhookEventHandler extends AbstractEventHandler<WebhookRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<WebhookRequest, ?> messageHandlerService;

  public MattermostWebhookEventHandler(MattermostService mattermostService,
                              MessageHandlerService<WebhookRequest, ?> messageHandlerService) {
    this.mattermostService = mattermostService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<WebhookRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Event Webhook: {}", eventDto);
    WebhookRequest event = eventDto.getEvent();
    processEvent(event);
    return null;
  }

  private void processEvent(WebhookRequest event) {
    EventMessage<WebhookRequest> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(event.getChannelId());
    message.setText(event.getText());
    messageHandlerService.processMessage(message);
  }

}
