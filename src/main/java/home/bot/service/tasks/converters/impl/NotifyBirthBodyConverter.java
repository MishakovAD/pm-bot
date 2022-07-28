package home.bot.service.tasks.converters.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.service.tasks.converters.AbstractBodyConverter;
import home.bot.service.tasks.converters.dto.BirthdayTaskDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("birthdayReminderBodyConverter")
@Slf4j(topic = "BODY_CONVERTER.NOTIFY_BIRTH")
public class NotifyBirthBodyConverter extends AbstractBodyConverter<BirthdayTaskDto> {

  public NotifyBirthBodyConverter(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  @SneakyThrows
  public BirthdayTaskDto convert(byte[] body) {
    return objectMapper.readValue(body, BirthdayTaskDto.class);
  }
}
