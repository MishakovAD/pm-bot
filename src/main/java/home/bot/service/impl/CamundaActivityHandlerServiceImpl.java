package home.bot.service.impl;

import static home.bot.dto.enums.CamundaActivity.receiveValueByProcess;

import home.bot.dto.CamundaActivityDto;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.common.CamundaEventMessageCommon;
import home.bot.dto.enums.CamundaActivity;
import home.bot.service.CamundaActivityHandlerService;
import home.bot.service.CamundaService;
import home.bot.service.camunda_activities_handlers.ActivityHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class CamundaActivityHandlerServiceImpl<Y> implements CamundaActivityHandlerService<Y> {

  private final CamundaService camundaService;
  private final Map<String, ActivityHandler<Y>> activityHandlers;

  @Override
  public void handleCamundaActivity(CamundaActivityDto<Y> camundaActivityDto, CamundaEventMessageCommon response) {
    String businessKey = camundaActivityDto.getBusinessKey();
    Map<ProcessInstance, ActivityInstance> activities = camundaService.getActiveInstanceByBusinessKey(businessKey);
    activities.entrySet()
        .stream()
        .findAny()
        .ifPresent(entry -> {
          ProcessInstance process = entry.getKey();
          String activityId = entry.getValue().getActivityId();
          enrichByActivityId(camundaActivityDto, response, activityId);
          getHandler(process.getProcessDefinitionId().split(":")[0])
              .handleCamundaActivity(camundaActivityDto, response);
        });
  }

  private ActivityHandler<Y> getHandler(String processName) {
    CamundaActivity handler = receiveValueByProcess(processName);
    return activityHandlers.get(handler.getHandlerName());
  }

  private void enrichByActivityId(CamundaActivityDto<Y> camundaActivityDto,
                                  CamundaEventMessageCommon response,
                                  String activityId) {
    camundaActivityDto.setActivityId(activityId);
    response.setActivityId(activityId);
  }
}
