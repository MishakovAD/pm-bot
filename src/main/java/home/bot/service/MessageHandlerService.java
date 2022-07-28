package home.bot.service;

import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;

public interface MessageHandlerService<T, Y> {
  EventMessageResponse processMessage(EventMessage<T> message);
}
