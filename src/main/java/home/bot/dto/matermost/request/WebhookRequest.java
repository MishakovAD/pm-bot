package home.bot.dto.matermost.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import home.bot.dto.enums.MattermostRequestType;
import lombok.Data;

@Data
public class WebhookRequest extends BaseMattermostRequest {
  private Long timestamp;
  private String postId;
  private String triggerWord;
  private String fileIds;

  @JsonCreator
  public WebhookRequest(String token, String team_id, String team_domain, String channel_id, String channel_name, Long timestamp,
      String user_id, String user_name, String post_id, String text, String trigger_word, String file_ids) {
    this.requestType = MattermostRequestType.WEBHOOK;
    this.token = token;
    this.teamId = team_id;
    this.teamDomain = team_domain;
    this.channelId = channel_id;
    this.channelName = channel_name;
    this.userId = user_id;
    this.userName = user_name;
    this.timestamp = timestamp;
    this.text = text;
    this.postId = post_id;
    this.triggerWord = trigger_word;
    this.fileIds = file_ids;
  }

}
