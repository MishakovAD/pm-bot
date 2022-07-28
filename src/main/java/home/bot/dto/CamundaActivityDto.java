package home.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CamundaActivityDto<T> {
  private String businessKey;
  private String activityId;
  private T objToCamunda;

}
