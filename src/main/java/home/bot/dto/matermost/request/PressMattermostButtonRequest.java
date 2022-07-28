package home.bot.dto.matermost.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PressMattermostButtonRequest extends BaseMattermostRequest {
  @JsonProperty("post_id")
  private String postId;
  @JsonProperty("trigger_id")
  private String triggerId;
  private String type;
  @JsonProperty("data_source")
  private String dataSource;
  private Map<String, String> context;

}
