package home.bot.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class NotifyAddressDto {
  protected String email;
  protected String phone;
  protected String chatId;
}
