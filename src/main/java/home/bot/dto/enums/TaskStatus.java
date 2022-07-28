package home.bot.dto.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
  NEW, //появился и не в очереди
  IN_PROGRESS, //в очереди на выполнение
  DONE,
  ERROR;
}
