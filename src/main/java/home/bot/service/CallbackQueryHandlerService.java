package home.bot.service;

import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryHandlerService<T> {
  EventMessageResponse processCallbackQuery(EventMessage<T> message);
}
