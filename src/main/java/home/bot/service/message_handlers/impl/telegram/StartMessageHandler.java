package home.bot.service.message_handlers.impl.telegram;

import home.bot.camunda.BpmnProcess;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageTgResponse;
import home.bot.service.CamundaActivityHandlerService;
import home.bot.service.CamundaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("startMessage")
@Slf4j(topic = "MESSAGE_HANDLER.START")
public class StartMessageHandler<T, Y> extends AbstractTelegramMessageHandler<T, Y> {


  public StartMessageHandler(CamundaService camundaService,
      CamundaActivityHandlerService<Y> activityHandlerService) {
    super(camundaService, activityHandlerService);
  }

  @Override
  public EventMessageTgResponse processMessage(EventMessage<T> message) {
    String chatId = message.getChatId();
    EventMessageTgResponse response = EventMessageTgResponse.builder()
        .chatId(chatId)
        .responseText("Привет! Давай познакомимся. Введи, пожалуйста, свои ФИО полностью через пробел.")
        .build();
    try {
      camundaService.start(BpmnProcess.TG_USER_REGISTRATION, chatId);
    } catch (Exception e) {
      response.setResponseText(e.getMessage());
    }

    return response;
  }
}
