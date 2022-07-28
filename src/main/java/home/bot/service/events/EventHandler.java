package home.bot.service.events;

import home.bot.dto.EventDto;

public interface EventHandler<T, R> {
  R handleEvent(EventDto<T> eventDto);
}
