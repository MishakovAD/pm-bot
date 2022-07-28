package home.bot.dto.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum CamundaActivity {
  TG_USER_REGISTRATION("tgRegistrationHandler", "tg_user_registration"),
  DEFAULT("defaultActivityHandler", "anyProcessName");

  private final String handlerName;
  private final String processName;

  private static final Set<CamundaActivity> CACHE = Stream.of(CamundaActivity.values())
      .collect(Collectors.toSet());

  public static CamundaActivity receiveValueByProcess(String process) {
    return CACHE.stream()
        .filter(camunda -> StringUtils.equals(process, camunda.getProcessName()))
        .findFirst()
        .orElse(DEFAULT);
  }
}
