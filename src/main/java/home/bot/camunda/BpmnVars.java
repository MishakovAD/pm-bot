package home.bot.camunda;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BpmnVars {
  FULL_NAME("fullName"),
  BIRTHDAY("birthday"),
  USER_ROLE("userRole"),
  USER_EMAIL("userEmail"),
  USER_PHONE("userPhone")
  ;

  private final String value;
}
