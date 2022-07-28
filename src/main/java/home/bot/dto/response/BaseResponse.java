package home.bot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
  @Builder.Default
  private String message = "Success";
  @Builder.Default
  private int code = 0;
  @Builder.Default
  private String traceId = MDC.get("traceId");
}
