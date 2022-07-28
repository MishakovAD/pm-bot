package home.bot.service.impl;

import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.service.CallbackQueryHandlerService;
import home.bot.service.callback_query_handlers.CallbackQueryHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class CallbackQueryHandlerServiceImpl<T> implements CallbackQueryHandlerService<T> {

  private final Map<String, CallbackQueryHandler<T>> callbackQueryHandlers;

  @Override
  public EventMessageResponse processCallbackQuery(EventMessage<T> message) {
    String chatId = message.getChatId();
    return EventMessageResponse.builder()
        .chatId(chatId)
        .build();
  }
}
