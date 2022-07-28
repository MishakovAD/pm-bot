package home.bot.exception;

public class CamundaProcessException extends RuntimeException {

  private int code;
  private String message;

  public CamundaProcessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }
}
