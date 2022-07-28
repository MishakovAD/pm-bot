package home.bot.exception.handler;

import home.bot.dto.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j(topic = "HANDLER")
public class GlobalExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse commonHandle(RuntimeException e) {
    LOGGER.error("RuntimeException. Error message: {}", e.getMessage(), e);
    return BaseResponse.builder()
        .message(e.getMessage())
        .code(-999)
        .build();
  }

}
