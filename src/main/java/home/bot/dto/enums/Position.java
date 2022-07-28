package home.bot.dto.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum Position {
  DEVELOPER("Разработчик"),
  ANALYST("Аналитик"),
  TESTER("Тестировщик");

  private final String position;

  private static final Set<Position> CACHE = Stream.of(Position.values())
      .collect(Collectors.toSet());

  public static Position receivePosition(String value) {
    return CACHE.stream()
        .filter(val -> StringUtils.equals(value, val.getPosition()))
        .findFirst()
        .orElse(null);
  }
}
