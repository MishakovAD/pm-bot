package home.bot.dto;

import home.bot.dto.enums.EventSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto<T> {
  private EventSource source;
  private T event;
}
