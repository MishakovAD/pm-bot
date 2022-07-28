package home.bot.dto.enum_converters;

import home.bot.dto.enums.TaskType;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TaskTypeConverter implements AttributeConverter<TaskType, String> {

  @Override
  public String convertToDatabaseColumn(TaskType attribute) {
    return Objects.nonNull(attribute) ? attribute.getType() : null;
  }

  @Override
  public TaskType convertToEntityAttribute(String dbData) {
    return TaskType.receiveTaskType(dbData);
  }
}
