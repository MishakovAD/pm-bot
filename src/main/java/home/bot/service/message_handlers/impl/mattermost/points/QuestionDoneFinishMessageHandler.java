package home.bot.service.message_handlers.impl.mattermost.points;

import static home.bot.dto.enums.MattermostDialogElementValue.OPEN_USER_QUESTIONS;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.service.QuestionService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("finishQuestionDoneMessage")
@Slf4j(topic = "MESSAGE_HANDLER.QUESTION_DONE_FINISH")
public class QuestionDoneFinishMessageHandler<T, Y> extends AbstractMattermostMessageHandler<BaseMattermostSubmitDialogRequest, Y> {

  private final QuestionService questionService;

  public QuestionDoneFinishMessageHandler(QuestionService questionService,
                                          MattermostService mattermostService,
                                          InteractiveMattermostClient mattermostClient,
                                          MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.questionService = questionService;
  }

  @Override
  public EventMessageResponse processMessage(EventMessage<BaseMattermostSubmitDialogRequest> message) {
    LOGGER.info("Start process message.");
    BaseMattermostSubmitDialogRequest request = message.getMessage();
    Map<String, Object> questions = request.getSubmission();
    Optional.ofNullable(questions.get(OPEN_USER_QUESTIONS.getName()))
        .map(String::valueOf)
        .map(Long::valueOf)
        .ifPresent(questionService::closeQuestion);
    return null;
  }
}
