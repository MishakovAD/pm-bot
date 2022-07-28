package home.bot.service.message_handlers.impl.telegram;

import home.bot.dto.CamundaActivityDto;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageTgResponse;
import home.bot.service.CamundaActivityHandlerService;
import home.bot.service.CamundaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("defaultMessageHandler")
@Slf4j(topic = "MESSAGE_HANDLER.DEFAULT")
public class DefaultMessageHandler<T> extends AbstractTelegramMessageHandler<T, String> {

  public DefaultMessageHandler(CamundaService camundaService,
      CamundaActivityHandlerService<String> activityHandlerService) {
    super(camundaService, activityHandlerService);
  }

  @Override
  public EventMessageTgResponse processMessage(EventMessage<T> message) {
    String chatId = message.getChatId();
    EventMessageTgResponse response = new EventMessageTgResponse();
    CamundaActivityDto<String> camundaActivityDto = CamundaActivityDto.<String>builder()
        .businessKey(chatId)
        .objToCamunda(message.getText())
        .build();
    activityHandlerService.handleCamundaActivity(camundaActivityDto, response);
    enrichTextResponseIfBlank(response);
    response.setChatId(chatId);
    return response;
  }

  private void enrichTextResponseIfBlank(EventMessageTgResponse response) {
    if (StringUtils.isBlank(response.getResponseText())) {
      response.setResponseText("Извини, но вести диалоги на светские беседы я пока не умею. Выбери команду из списка.");
    }
  }
}
