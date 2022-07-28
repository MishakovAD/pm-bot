package home.bot.service.camunda_activities_handlers.impl;

import home.bot.camunda.BpmnProcess;
import home.bot.dto.CamundaActivityDto;
import home.bot.dto.EventMessageTgResponse;
import home.bot.dto.common.CamundaEventMessageCommon;
import home.bot.service.CamundaService;
import home.bot.service.DmnEngineService;
import home.bot.service.camunda_activities_handlers.AbstractActivityHandler;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("tgRegistrationHandler")
@Slf4j(topic = "ACTIVITY_HANDLER.TG_REGISTRATION")
public class TgRegistrationActivityHandler extends AbstractActivityHandler<String> {

  public TgRegistrationActivityHandler(CamundaService camundaService, DmnEngineService dmnEngineService) {
    super(camundaService, dmnEngineService);
  }

  @Override
  public void handleCamundaActivity(CamundaActivityDto<String> camundaActivityDto, CamundaEventMessageCommon response) {
    String businessKey = camundaActivityDto.getBusinessKey();
    String activityId = camundaActivityDto.getActivityId();
    Map<String, Object> result = dmnEngineService
        .getEntryMap(TG_USER_REG_ANSWER, TG_USER_REG_DECISION_KEY, Map.of("activityName", activityId));
    camundaService.createMessageCorrelation(BpmnProcess.TG_USER_REGISTRATION,
        businessKey,
        String.valueOf(result.get("correlateMessageVal")),
        Map.of(String.valueOf(result.get("resultVarName")), camundaActivityDto.getObjToCamunda()));
    ((EventMessageTgResponse) response).setResponseText(String.valueOf(result.get("message")));
  }
}
