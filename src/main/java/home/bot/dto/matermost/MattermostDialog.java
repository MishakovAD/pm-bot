package home.bot.dto.matermost;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MattermostDialog {

  private String callback_id;
  private String title;
  private String icon_url;
  private String introduction_text;
  private String submit_label;
  private String state;
  private boolean notify_on_cancel;
  private List<MattermostDialogElement> elements;

}
