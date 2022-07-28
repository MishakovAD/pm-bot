package home.bot.service.callback_query_handlers;

import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;

public interface CallbackQueryHandler<T> {
  EventMessageResponse processCallBack(EventMessage<T> message);
}
