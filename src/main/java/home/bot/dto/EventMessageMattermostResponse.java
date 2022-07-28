package home.bot.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class EventMessageMattermostResponse extends EventMessageResponse{
  private String any;
}
