package home.bot.service;

import home.bot.dto.enums.EventSource;

public interface EventHandlerService<T, R> {
  R handleEvent(EventSource source, T eventBody);
}
