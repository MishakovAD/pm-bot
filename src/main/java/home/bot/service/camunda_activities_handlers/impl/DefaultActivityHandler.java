package home.bot.service.camunda_activities_handlers.impl;

import home.bot.dto.CamundaActivityDto;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.common.CamundaEventMessageCommon;
import home.bot.service.CamundaService;
import home.bot.service.DmnEngineService;
import home.bot.service.camunda_activities_handlers.AbstractActivityHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("defaultActivityHandler")
@Slf4j(topic = "ACTIVITY_HANDLER.DEFAULT")
public class DefaultActivityHandler<Y> extends AbstractActivityHandler<Y> {

  public DefaultActivityHandler(CamundaService camundaService, DmnEngineService dmnEngineService) {
    super(camundaService, dmnEngineService);
  }

  @Override
  public void handleCamundaActivity(CamundaActivityDto<Y> camundaActivityDto, CamundaEventMessageCommon response) {
    String businessKey = camundaActivityDto.getBusinessKey();
    String activityId = camundaActivityDto.getActivityId();
    LOGGER.info("Default activity handler! Handler for process with businessKey: {} and activityId: {} not found!", businessKey, activityId);
  }
}
