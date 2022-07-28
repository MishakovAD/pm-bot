package home.bot.dto;

import home.bot.dto.common.CamundaEventMessageCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EventMessageTgResponse extends EventMessageResponse implements CamundaEventMessageCommon {
  private String responseText;
  private String activityId;
}
