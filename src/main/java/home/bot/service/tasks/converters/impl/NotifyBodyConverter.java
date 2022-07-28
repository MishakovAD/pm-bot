package home.bot.service.tasks.converters.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.service.tasks.converters.AbstractBodyConverter;
import home.bot.service.tasks.converters.dto.BirthdayTaskDto;
import home.bot.service.tasks.converters.dto.TaskDto;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("notifyBodyConverter")
@Slf4j(topic = "BODY_CONVERTER.NOTIFY")
public class NotifyBodyConverter extends AbstractBodyConverter<TaskDto> {

  public NotifyBodyConverter(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  @SneakyThrows
  public TaskDto convert(byte[] body) {
    return objectMapper.readValue(body, TaskDto.class);
  }
}
