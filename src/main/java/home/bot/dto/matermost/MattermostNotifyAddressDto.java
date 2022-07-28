package home.bot.dto.matermost;

import home.bot.dto.NotifyAddressDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class MattermostNotifyAddressDto extends NotifyAddressDto {
  private String dialogId;
}
