package home.bot.service.events.impl.jira;

import home.bot.dto.EventDto;
import home.bot.dto.jira.JiraEvent;
import home.bot.service.events.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("JiraEventHandler")
@Slf4j(topic = "EVENT_HANDLER.JIRA")
public class JiraEventHandler extends AbstractEventHandler<JiraEvent, Object> {

  @Override
  public Object handleEvent(EventDto<JiraEvent> eventDto) {
    LOGGER.info("Start handle event from jira. Event: {}", eventDto);
    return null;
  }
}
