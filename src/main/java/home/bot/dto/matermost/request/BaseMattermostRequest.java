package home.bot.dto.matermost.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import home.bot.dto.enums.MattermostRequestType;
import lombok.Data;

@Data
public class BaseMattermostRequest {
  protected MattermostRequestType requestType;
  @JsonProperty("channel_id")
  protected String channelId;
  @JsonProperty("user_id")
  protected String userId;
  @JsonProperty("team_id")
  protected String teamId;
  @JsonProperty("channel_name")
  protected String channelName;
  @JsonProperty("user_name")
  protected String userName;
  protected String token;
  @JsonProperty("team_domain")
  protected String teamDomain;
  protected String text;

}
