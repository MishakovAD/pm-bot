package home.bot.service.camunda_activities_handlers;

import home.bot.dto.CamundaActivityDto;
import home.bot.dto.common.CamundaEventMessageCommon;

public interface ActivityHandler<Y> {
  void handleCamundaActivity(CamundaActivityDto<Y> camundaActivityDto, CamundaEventMessageCommon response);
}
