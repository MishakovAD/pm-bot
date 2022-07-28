package home.bot.service.message_handlers.impl.mattermost.tasks;

import static home.bot.config.LoggingFilterConfig.MDC_TRACE_ID;
import static home.bot.controller.MattermostTaskController.MATTERMOST_USER_TASK_REMOVE_FINISH;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_POINTS_OPEN_QUESTIONS;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_POINTS_TYPES;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.enums.PointType;
import home.bot.dto.matermost.MattermostDialog;
import home.bot.dto.matermost.MattermostDialogElement;
import home.bot.dto.matermost.request.OpenDialogRequest;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service("startMattermostRemoveUserTaskMessage")
@Slf4j(topic = "MESSAGE_HANDLER.TASKS")
public class RemoveUserTaskStartMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  public RemoveUserTaskStartMessageHandler(MattermostService mattermostService,
                                          InteractiveMattermostClient mattermostClient,
                                          MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    LOGGER.info("Start process message.");
    PressMattermostButtonRequest request = message.getMessage();
    String triggerId = request.getTriggerId();
//    mattermostClient.openDialog(createRegistrationRequest(triggerId));
    return null;
  }

  private OpenDialogRequest createRegistrationRequest(String triggerId) {
    //todo: реализовать
    MattermostDialogElement pointType = mattermostElementBuilder.createSelectDialogElement(USER_POINTS_TYPES, createPointTypesMap());
    MattermostDialogElement openQuestions = mattermostElementBuilder.createDefaultDialogElement(USER_POINTS_OPEN_QUESTIONS);
    return OpenDialogRequest.builder()
        .trigger_id(triggerId)
        .url(RESPONSE_HOST + MATTERMOST_USER_TASK_REMOVE_FINISH)
        .dialog(MattermostDialog.builder()
            .callback_id(MDC.get(MDC_TRACE_ID))
            .title("Создание задания к выполнению.")
            .icon_url(null)
            .elements(List.of(pointType, openQuestions))
            .notify_on_cancel(true)
            .build())
        .build();
  }

  private Map<String, String> createPointTypesMap() {
    return Stream.of(PointType.values()).collect(Collectors.toMap(PointType::getType, PointType::name));
  }

}
