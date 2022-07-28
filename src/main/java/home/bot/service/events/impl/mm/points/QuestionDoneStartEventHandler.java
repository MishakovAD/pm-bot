package home.bot.service.events.impl.mm.points;

import static home.bot.dto.enums.MessageHandlerType.QUESTION_DONE_MATTERMOST_START;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.mattermost.MattermostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostUserQuestionDoneStartEventHandler")
public class QuestionDoneStartEventHandler extends AbstractEventHandler<PressMattermostButtonRequest, Object> {

  private MattermostService mattermostService;
  private MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService;

  public QuestionDoneStartEventHandler(MattermostService mattermostService,
                              MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService) {
    this.mattermostService = mattermostService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<PressMattermostButtonRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Start done question: {}", eventDto);
    PressMattermostButtonRequest event = eventDto.getEvent();
    processEvent(event);
    return null;
  }

  private void processEvent(PressMattermostButtonRequest event) {
    EventMessage<PressMattermostButtonRequest> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(event.getTriggerId());
    message.setText(QUESTION_DONE_MATTERMOST_START.getCommand());
    messageHandlerService.processMessage(message);
  }

}
