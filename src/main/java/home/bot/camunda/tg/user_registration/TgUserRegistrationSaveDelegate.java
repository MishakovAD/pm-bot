package home.bot.camunda.tg.user_registration;

import home.bot.camunda.BpmnVars;
import home.bot.dto.enums.Position;
import home.bot.dto.UserRegistrationDto;
import home.bot.service.CamundaService;
import home.bot.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("tgUserRegistrationSave")
public class TgUserRegistrationSaveDelegate implements JavaDelegate {

  private final CamundaService camundaService;
  private final UserRegistrationService userRegistrationService;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String fullName = (String) camundaService.getVariable(BpmnVars.FULL_NAME.getValue(), execution);
    String[] args = fullName.split(" ");
    String lastName = args[0];
    String firstName = args[1];
    String middleName = args[2];
    String birthday = (String) camundaService.getVariable(BpmnVars.BIRTHDAY.getValue(), execution);
    String role = (String) camundaService.getVariable(BpmnVars.USER_ROLE.getValue(), execution);
    String email = (String) camundaService.getVariable(BpmnVars.USER_EMAIL.getValue(), execution);
    String phone = (String) camundaService.getVariable(BpmnVars.USER_PHONE.getValue(), execution);

    userRegistrationService.createUser(UserRegistrationDto.builder()
        .firstName(firstName)
        .middleName(middleName)
        .lastName(lastName)
        .birthday(birthday)
        .position(Position.receivePosition(role))
        .email(email)
        .phone(phone)
        .chatId(execution.getBusinessKey())
        .build());
  }
}
