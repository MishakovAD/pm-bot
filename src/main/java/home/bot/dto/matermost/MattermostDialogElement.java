package home.bot.dto.matermost;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MattermostDialogElement {

  @JsonProperty("display_name")
  private String displayName;
  private String name;
  private String type;
  private String subtype;
  @JsonProperty("default")
  private String defaultText;
  private String placeholder;
  @JsonProperty("help_text")
  private String helpText;
  private boolean optional;
  @JsonProperty("min_length")
  private Long minLength;
  @JsonProperty("max_length")
  private Long maxLength;
  @JsonProperty("data_source")
  private String dataSource;
  private List<MattermostElementOption> options;
  @JsonProperty("submit_label")
  private String submitLabel;
  @JsonProperty("notify_on_cancel")
  private boolean notifyOnCancel;
  private String state;
}
