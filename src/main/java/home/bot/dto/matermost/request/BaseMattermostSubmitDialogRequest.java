package home.bot.dto.matermost.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseMattermostSubmitDialogRequest extends BaseMattermostRequest {
  protected String type;
  @JsonProperty("callback_id")
  protected String callbackId;
  protected String state;
  @JsonProperty("user_id")
  protected String userId;
  @JsonProperty("channel_id")
  protected String channelId;
  @JsonProperty("team_id")
  protected String teamId;
  protected boolean cancelled;
  private Map<String, Object> submission;
}
