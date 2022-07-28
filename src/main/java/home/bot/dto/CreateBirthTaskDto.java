package home.bot.dto;

import home.bot.dto.enums.NotificationSource;
import home.bot.dto.enums.TaskType;
import home.bot.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateBirthTaskDto extends CreateTaskDto {

  private String birthUserName;

  public CreateBirthTaskDto(NotificationSource source, User user) {
    super(source);
    super.setType(TaskType.BIRTHDAY_REMINDER);
    this.birthUserName = String.format(USER_NAME_PATTERN, user.getFirstName(), user.getLastName());
    LocalDateTime notificationBirthDate = createNotificationBirthDate(user.getBirthday());
    super.setExecutionDate(notificationBirthDate.toLocalDate());
    super.setExecutionTime(notificationBirthDate.toLocalTime());
  }

  private static final String USER_NAME_PATTERN = "%s %s";

  private LocalDateTime createNotificationBirthDate(LocalDate birthday) {
    LocalDateTime dateTime = LocalDate.now()
        .withMonth(birthday.getMonth().getValue())
        .withDayOfMonth(birthday.getDayOfMonth())
        .minusDays(3L)
        .atTime(12, 0);
    if (birthday.getMonth().getValue() < LocalDate.now().getMonth().getValue()
        || (birthday.getMonth().getValue() == LocalDate.now().getMonth().getValue() && birthday.getDayOfMonth() <= LocalDate.now().getDayOfMonth())) {
      return dateTime.plusYears(1);
    }
    return dateTime;
  }
}
