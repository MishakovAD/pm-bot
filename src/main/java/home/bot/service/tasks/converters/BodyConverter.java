package home.bot.service.tasks.converters;

public interface BodyConverter<T> {
  T convert(byte[] body);
}
