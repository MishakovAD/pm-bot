package home.bot.service.events.impl.mm.comments;

import static home.bot.camunda.BpmnProcess.MM_REFRESH_MESSAGE;
import static home.bot.dto.enums.MessageHandlerType.ADD_COMMENT_TO_QUESTION_MATTERMOST_START;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.CamundaService;
import home.bot.service.MessageHandlerService;
import home.bot.service.events.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EVENT_HANDLER.MATTERMOST")
@Service("MattermostAddCommentToQuestionStartEventHandler")
public class AddCommentToQuestionStartEventHandler extends AbstractEventHandler<PressMattermostButtonRequest, Object> {

  private CamundaService camundaService;
  private MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService;

  public AddCommentToQuestionStartEventHandler(CamundaService camundaService,
                              MessageHandlerService<PressMattermostButtonRequest, ?> messageHandlerService) {
    this.camundaService = camundaService;
    this.messageHandlerService = messageHandlerService;
  }

  @Override
  public Object handleEvent(EventDto<PressMattermostButtonRequest> eventDto) {
    LOGGER.info("Start handle event from mattermost. Start add comment to question: {}", eventDto);
    PressMattermostButtonRequest event = eventDto.getEvent();
    processEvent(event);
    return null;
  }

  private void processEvent(PressMattermostButtonRequest event) {
    EventMessage<PressMattermostButtonRequest> message = new EventMessage<>();
    message.setMessage(event);
    message.setChatId(event.getTriggerId());
    message.setText(ADD_COMMENT_TO_QUESTION_MATTERMOST_START.getCommand());
    messageHandlerService.processMessage(message);

    camundaService.start(MM_REFRESH_MESSAGE, event.getChannelId());
  }

}
