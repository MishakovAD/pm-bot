package home.bot.dto;

import home.bot.dto.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
  private String firstName;
  private String middleName;
  private String lastName;
  private String birthday;
  private Position position;
  private String email;
  private String phone;
  private String chatId;
}
