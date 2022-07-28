package home.bot.dto.enum_converters;

import home.bot.dto.enums.Position;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PositionConverter implements AttributeConverter<Position, String> {

  @Override
  public String convertToDatabaseColumn(Position attribute) {
    return Objects.nonNull(attribute) ? attribute.getPosition() : null;
  }

  @Override
  public Position convertToEntityAttribute(String dbData) {
    return Position.receivePosition(dbData);
  }
}
