package home.bot.service.tasks.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
@Slf4j(topic = "BODY_CONVERTER.ABSTRACT")
public abstract class AbstractBodyConverter<T> implements BodyConverter<T> {

  protected final ObjectMapper objectMapper;

}
