package home.bot.dto;

import home.bot.dto.enums.NotificationSource;
import home.bot.dto.enums.TaskPeriod;
import home.bot.dto.enums.TaskType;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class CreateTaskDto {
  private String name;
  private TaskType type;
  private TaskPeriod period;
  private LocalDate executionDate;
  private LocalTime executionTime;
  private String message;
  private Long groupIdForNotify;
  private NotificationSource source;

  public CreateTaskDto(NotificationSource source) {
    this.source = source;
  }

}
