package home.bot.service.message_handlers.impl.mattermost.other;

import static home.bot.config.LoggingFilterConfig.MDC_TRACE_ID;
import static home.bot.controller.MattermostUserController.MATTERMOST_USER_REGISTRATION;
import static home.bot.dto.enums.MattermostBotMenuType.USER_ALREADY_REGISTERED;
import static home.bot.dto.enums.MattermostDialogElementValue.REGISTRATION_BIRTHDATE;
import static home.bot.dto.enums.MattermostDialogElementValue.REGISTRATION_EMAIL;
import static home.bot.dto.enums.MattermostDialogElementValue.REGISTRATION_FIRST_NAME;
import static home.bot.dto.enums.MattermostDialogElementValue.REGISTRATION_LAST_NAME;
import static home.bot.dto.enums.MattermostDialogElementValue.REGISTRATION_MIDDLE_NAME;
import static home.bot.dto.enums.MattermostDialogElementValue.REGISTRATION_PHONE_NUMBER;
import static home.bot.dto.enums.MattermostDialogElementValue.REGISTRATION_POSITION;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.enums.Position;
import home.bot.dto.matermost.MattermostDialog;
import home.bot.dto.matermost.MattermostDialogElement;
import home.bot.dto.matermost.request.OpenDialogRequest;
import home.bot.dto.matermost.request.SlashCommandRequest;
import home.bot.service.UserService;
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

@Service("startRegisterMattermostMessage")
@Slf4j(topic = "MESSAGE_HANDLER.REGISTER_MATTERMOST")
public class StartRegisterMattermostMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<SlashCommandRequest, Y> {

  private final UserService userService;

  public StartRegisterMattermostMessageHandler(MattermostService mattermostService,
                                          InteractiveMattermostClient mattermostClient,
                                          MattermostElementBuilder mattermostElementBuilder,
                                          UserService userService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.userService = userService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<SlashCommandRequest> message) {
    LOGGER.info("Start process message.");
    SlashCommandRequest request = message.getMessage();
    checkUserExists(request);
    mattermostClient.openDialog(createRegistrationRequest(request.getTriggerId()));
    return null;
  }

  private void checkUserExists(SlashCommandRequest request) {
    String userId = request.getUserId();
    if (userService.isUserExists(userId)) {
//      mattermostService.sendMainBotMenuToUser(userId, USER_ALREADY_REGISTERED); //todo: после MVP включить. Пока без меню для пользователей
      mattermostService.sendMessageToUser(userId, "Ты уже есть у меня в базе (:"); //todo: remove
      throw new RuntimeException(String.format("User with mm_id: %s already exists", userId));
    }
  }

  private OpenDialogRequest createRegistrationRequest(String triggerId) {
    MattermostDialogElement lastName = mattermostElementBuilder.createDefaultDialogElement(REGISTRATION_LAST_NAME);
    MattermostDialogElement firstName = mattermostElementBuilder.createDefaultDialogElement(REGISTRATION_FIRST_NAME);
    MattermostDialogElement middleName = mattermostElementBuilder.createDefaultDialogElement(REGISTRATION_MIDDLE_NAME);
    MattermostDialogElement birth = mattermostElementBuilder.createDefaultDialogElement(REGISTRATION_BIRTHDATE);
    MattermostDialogElement position = mattermostElementBuilder.createSelectDialogElement(REGISTRATION_POSITION, createPositionsMap());
    MattermostDialogElement phone = mattermostElementBuilder.createDefaultDialogElement(REGISTRATION_PHONE_NUMBER);
    MattermostDialogElement email = mattermostElementBuilder.createDefaultDialogElement(REGISTRATION_EMAIL);
    return OpenDialogRequest.builder()
        .trigger_id(triggerId)
        .url(RESPONSE_HOST + MATTERMOST_USER_REGISTRATION)
        .dialog(MattermostDialog.builder()
            .callback_id(MDC.get(MDC_TRACE_ID))
            .title("Форма регистрации.")
            .icon_url(null)
            .introduction_text("Привет, давай познакомимся! Я буду отличным помощником.")
            .elements(List.of(lastName, firstName, middleName, birth, position, phone, email))
            .notify_on_cancel(true)
            .build())
        .build();
  }

  private Map<String, String> createPositionsMap() {
    return Stream.of(Position.values()).collect(Collectors.toMap(Position::getPosition, Position::name));
  }
}
