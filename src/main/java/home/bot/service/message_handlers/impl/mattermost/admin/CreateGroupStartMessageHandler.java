package home.bot.service.message_handlers.impl.mattermost.admin;

import static home.bot.config.LoggingFilterConfig.MDC_TRACE_ID;
import static home.bot.controller.MattermostAdminCommandController.MATTERMOST_ADMIN_CREATE_GROUP_FINISH;
import static home.bot.dto.enums.MattermostDialogElementValue.MM_CREATE_GROUP_VALUES;
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

@Service("startCreateGroupMessage")
@Slf4j(topic = "MESSAGE_HANDLER.CREATE_GROUP_MATTERMOST")
public class CreateGroupStartMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  public CreateGroupStartMessageHandler(MattermostService mattermostService,
                                          InteractiveMattermostClient mattermostClient,
                                          MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    LOGGER.info("Start process message.");
    PressMattermostButtonRequest request = message.getMessage();
    String triggerId = request.getTriggerId();
    mattermostClient.openDialog(createRegistrationRequest(triggerId));
    return null;
  }

  private OpenDialogRequest createRegistrationRequest(String triggerId) {
    List<MattermostDialogElement> elements = MM_CREATE_GROUP_VALUES.stream()
        .map(mattermostElementBuilder::createDefaultDialogElement)
        .toList();
    return OpenDialogRequest.builder()
        .trigger_id(triggerId)
        .url(RESPONSE_HOST + MATTERMOST_ADMIN_CREATE_GROUP_FINISH)
        .dialog(MattermostDialog.builder()
            .callback_id(MDC.get(MDC_TRACE_ID))
            .title("Вот и настал тот день, когда у тебя появилась своя группа!")
            .icon_url(null)
            .elements(elements)
            .notify_on_cancel(true)
            .build())
        .build();
  }

  private Map<String, String> createPointTypesMap() {
    return Stream.of(PointType.values()).collect(Collectors.toMap(PointType::getType, PointType::name));
  }

}
