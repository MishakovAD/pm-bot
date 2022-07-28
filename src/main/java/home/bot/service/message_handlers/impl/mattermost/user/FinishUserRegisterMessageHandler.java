package home.bot.service.message_handlers.impl.mattermost.user;

import static home.bot.dto.enums.MattermostDialogElementValue.MM_USER_REGISTRATION_MAPPING;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.UserRegistrationDto;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("finishRegisterMattermostMessage")
@Slf4j(topic = "MESSAGE_HANDLER.REGISTER_MATTERMOST")
public class FinishUserRegisterMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<BaseMattermostSubmitDialogRequest, Y> {

  public FinishUserRegisterMessageHandler(MattermostService mattermostService,
                                                InteractiveMattermostClient mattermostClient,
                                                MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<BaseMattermostSubmitDialogRequest> message) {
    LOGGER.info("Start process message.");
    BaseMattermostSubmitDialogRequest request = message.getMessage();
    Map<String, Object> registrationData = request.getSubmission();
    UserRegistrationDto userDto = new UserRegistrationDto();
    userDto.setChatId(request.getUserId());
    registrationData.entrySet()
        .stream()
        .filter(entry -> Objects.nonNull(entry.getValue()))
        .forEach(entry -> {
      MM_USER_REGISTRATION_MAPPING.get(entry.getKey()).accept(userDto, String.valueOf(entry.getValue()));
    });
    mattermostService.userRegistration(userDto);
    return null;
  }
}
