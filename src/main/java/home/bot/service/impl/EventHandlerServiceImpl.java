package home.bot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.dto.EventDto;
import home.bot.dto.enums.EventSource;
import home.bot.entity.Event;
import home.bot.repository.EventRepository;
import home.bot.service.EventHandlerService;
import home.bot.service.events.EventHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class EventHandlerServiceImpl<T, R> implements EventHandlerService<T, R> {

  private final ObjectMapper objectMapper;
  private final EventRepository eventRepository;
  private final Map<String, EventHandler<T, R>> handlers;

  @Override
  public R handleEvent(EventSource source, T eventBody) {
    EventDto<T> event = new EventDto<>();
    event.setSource(source);
    event.setEvent(eventBody);
    saveEvent(source, eventBody);
    return handlers.get(source.getSource()).handleEvent(event);
  }

  @SneakyThrows
  private void saveEvent(EventSource source, T eventBody) {
    Event event = new Event();
    event.setSource(source);
    event.setBody(objectMapper.writeValueAsBytes(eventBody));
    eventRepository.save(event);
  }
}
