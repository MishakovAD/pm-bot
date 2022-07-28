package home.bot.dto.matermost.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import home.bot.dto.enums.MattermostRequestType;
import lombok.Data;

@Data
public class SlashCommandRequest extends BaseMattermostRequest {
  private String command;
  private String responseUrl;
  private String triggerId;

  @JsonCreator
  public SlashCommandRequest(String token, String team_id, String team_domain, String channel_id, String channel_name,
      String user_id, String user_name, String command, String text, String response_url, String trigger_id) {
    this.requestType = MattermostRequestType.SLASH_COMMAND;
    this.token = token;
    this.teamId = team_id;
    this.teamDomain = team_domain;
    this.channelId = channel_id;
    this.channelName = channel_name;
    this.userId = user_id;
    this.userName = user_name;
    this.command = command;
    this.text = text;
    this.responseUrl = response_url;
    this.triggerId = trigger_id;
  }
}
