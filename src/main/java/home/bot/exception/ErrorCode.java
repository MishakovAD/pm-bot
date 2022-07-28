package home.bot.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  CAMUNDA_START_PROCESS(-1, "Более одного активного процесса запущено!");

  private final int code;
  private final String message;

}
