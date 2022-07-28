package home.bot.service.message_handlers;

import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;

public interface MessageHandler<T, Y> {
  EventMessageResponse processMessage(EventMessage<T> message);
}
