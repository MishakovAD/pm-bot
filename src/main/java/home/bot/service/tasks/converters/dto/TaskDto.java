package home.bot.service.tasks.converters.dto;

import home.bot.dto.enums.NotificationSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
  private Long addressId;
  private String message;
  private NotificationSource source;
}
