package home.bot.service;

import home.bot.dto.CamundaActivityDto;
import home.bot.dto.common.CamundaEventMessageCommon;

public interface CamundaActivityHandlerService<Y> {
  void handleCamundaActivity(CamundaActivityDto<Y> camundaActivityDto, CamundaEventMessageCommon response);
}
