package home.bot.service.message_handlers.impl.telegram;

import home.bot.service.CamundaActivityHandlerService;
import home.bot.service.CamundaService;
import home.bot.service.message_handlers.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class AbstractTelegramMessageHandler<T, Y> implements MessageHandler<T, Y> {
  protected final CamundaService camundaService;
  protected final CamundaActivityHandlerService<Y> activityHandlerService;
}
