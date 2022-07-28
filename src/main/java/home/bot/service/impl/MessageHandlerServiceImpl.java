package home.bot.service.impl;

import static home.bot.dto.enums.MessageHandlerType.receiveValueByMessage;

import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.enums.MessageHandlerType;
import home.bot.service.MessageHandlerService;
import home.bot.service.message_handlers.MessageHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class MessageHandlerServiceImpl<T, Y> implements MessageHandlerService<T, Y> {

  private final Map<String, MessageHandler<T, Y>> messageHandlers;

  @Override
  public EventMessageResponse processMessage(EventMessage<T> message) {
    return getHandler(message.getText()).processMessage(message);
  }

  private MessageHandler<T, Y> getHandler(String text) {
    MessageHandlerType handler = receiveValueByMessage(text);
    return messageHandlers.get(handler.getValue());
  }
}
