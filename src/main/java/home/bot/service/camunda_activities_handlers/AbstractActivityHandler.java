package home.bot.service.camunda_activities_handlers;

import home.bot.service.CamundaService;
import home.bot.service.DmnEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class AbstractActivityHandler<Y> implements ActivityHandler<Y> {

  protected static final String TG_USER_REG_ANSWER = "dmn/tg_user_registration_answer_by_activity.dmn";
  protected static final String TG_USER_REG_DECISION_KEY = "tg_registration_answer_text";

  protected final CamundaService camundaService;
  protected final DmnEngineService dmnEngineService;
}
