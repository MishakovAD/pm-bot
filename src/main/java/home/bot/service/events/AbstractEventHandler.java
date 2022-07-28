package home.bot.service.events;

import home.bot.dto.EventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "EVENT_HANDLER.ABSTRACT")
public abstract class AbstractEventHandler<T, R> implements EventHandler<T, R> {

  protected EventMessage<T> buildEventMessage(T event, String chatId, String command) {
    EventMessage<T> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(chatId);
    message.setText(command);
    return message;
  }

}
