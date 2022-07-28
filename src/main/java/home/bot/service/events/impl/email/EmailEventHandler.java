package home.bot.service.events.impl.email;

import home.bot.dto.EventDto;
import home.bot.service.events.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("EmailEventHandler")
@Slf4j(topic = "EVENT_HANDLER.EMAIL")
public class EmailEventHandler extends AbstractEventHandler<Object, Object> { //Вместо Object -> response type

  @Override
  public Object handleEvent(EventDto<Object> eventDto) {
    LOGGER.info("Start handle event from e-mail. Event: {}", eventDto);
    return null;
  }
}
