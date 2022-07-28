package home.bot.dto.matermost.request;

import home.bot.dto.matermost.MattermostDialog;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenDialogRequest {
  private String trigger_id;
  private String url;
  private MattermostDialog dialog;
}
