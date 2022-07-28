package home.bot.service.message_handlers.impl.mattermost.points;

import static home.bot.config.LoggingFilterConfig.MDC_TRACE_ID;
import static home.bot.controller.MattermostPointController.MATTERMOST_USER_POINTS_DONE_FINISH;
import static home.bot.dto.enums.MattermostDialogElementValue.OPEN_USER_QUESTIONS;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.matermost.MattermostDialog;
import home.bot.dto.matermost.MattermostDialogElement;
import home.bot.dto.matermost.request.OpenDialogRequest;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.entity.Question;
import home.bot.service.QuestionService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service("startQuestionDoneMessage")
@Slf4j(topic = "MESSAGE_HANDLER.QUESTION_DONE_START")
public class QuestionDoneStartMessageHandler<T, Y> extends AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  private final QuestionService questionService;

  public QuestionDoneStartMessageHandler(QuestionService questionService,
                                          MattermostService mattermostService,
                                          InteractiveMattermostClient mattermostClient,
                                          MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.questionService = questionService;
  }

  @Override
  public EventMessageResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    LOGGER.info("Start process message.");
    PressMattermostButtonRequest request = message.getMessage();
    String triggerId = request.getTriggerId();
    String userAddress = request.getUserId();
    mattermostClient.openDialog(createDoneQuestionsRequest(triggerId, userAddress));
    return null;
  }

  private OpenDialogRequest createDoneQuestionsRequest(String triggerId, String userAddress) {
    MattermostDialogElement questions = mattermostElementBuilder.createSelectDialogElement(OPEN_USER_QUESTIONS, createOpenQuestionsMap(userAddress));
    return OpenDialogRequest.builder()
        .trigger_id(triggerId)
        .url(RESPONSE_HOST + MATTERMOST_USER_POINTS_DONE_FINISH)
        .dialog(MattermostDialog.builder()
            .callback_id(MDC.get(MDC_TRACE_ID))
            .title("Вопросы, которые выполнены и можно закрыть.")
            .icon_url(null)
            .introduction_text("Привет, выбирай, не стесняйся.")
            .elements(List.of(questions))
            .notify_on_cancel(true)
            .build())
        .build();
  }

  private Map<String, String> createOpenQuestionsMap(String userAddress) {
    return questionService.findOpenUserQuestions(userAddress)
        .stream()
        .collect(Collectors.toMap(Question::getText, question -> String.valueOf(question.getId())));
  }
}
