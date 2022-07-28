package home.bot.camunda;

import java.util.Arrays;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum BpmnProcess {
  TG_USER_REGISTRATION("tg_user_registration"),
  MM_REFRESH_MESSAGE("mm_refresh_message");

  private String id;

  BpmnProcess(String id) {
    this.id = id;
  }

  public static BpmnProcess getByProcessId(String processId) {
    return Arrays.stream(values())
        .filter(process -> StringUtils.equalsIgnoreCase(process.getId(), processId))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Process with processId = %s not found", processId)));
  }
}
