package home.bot.service.message_handlers.impl.mattermost.comments;

import static home.bot.config.LoggingFilterConfig.MDC_TRACE_ID;
import static home.bot.controller.MattermostPointController.MATTERMOST_ADD_COMMENT_TO_QUESTION_FINISH;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_COMMENTS_TO_QUESTION;
import static home.bot.dto.enums.MattermostDialogElementValue.OPEN_USER_QUESTIONS;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.matermost.MattermostDialog;
import home.bot.dto.matermost.MattermostDialogElement;
import home.bot.dto.matermost.request.OpenDialogRequest;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service("startAddCommentToQuestionMessage")
@Slf4j(topic = "MESSAGE_HANDLER.START_ADD_COMMENT")
public class StartAddCommentToQuestionMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  public StartAddCommentToQuestionMessageHandler(MattermostService mattermostService,
                                                      InteractiveMattermostClient mattermostClient,
                                                      MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
  }

  @Override
  public EventMessageResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    LOGGER.info("Start process message.");
    PressMattermostButtonRequest request = message.getMessage();
    String triggerId = request.getTriggerId();
    mattermostClient.openDialog(createAddCommentsRequest(triggerId));
    return null;
  }

  private OpenDialogRequest createAddCommentsRequest(String triggerId) {
    MattermostDialogElement openQuestions = mattermostElementBuilder.createSelectDialogElement(OPEN_USER_QUESTIONS, createQuestionsMap());
    MattermostDialogElement comments = mattermostElementBuilder.createDefaultDialogElement(USER_COMMENTS_TO_QUESTION);
    return OpenDialogRequest.builder()
        .trigger_id(triggerId)
        .url(RESPONSE_HOST + MATTERMOST_ADD_COMMENT_TO_QUESTION_FINISH)
        .dialog(MattermostDialog.builder()
            .callback_id(MDC.get(MDC_TRACE_ID))
            .title("Добавить комментарии к вопросу.")
            .icon_url(null)
            .introduction_text("Привет! Тебе есть, что сказать?")
            .elements(List.of(openQuestions, comments))
            .notify_on_cancel(true)
            .build())
        .build();
  }

  private Map<String, String> createQuestionsMap() {
    return Map.of("Test id", "Test value question");
  }
}
