package home.bot.service.events.impl.tg;

import home.bot.dto.EventDto;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageResponse;
import home.bot.dto.EventMessageTgResponse;
import home.bot.dto.enums.KeyboardByActivity;
import home.bot.service.events.AbstractEventHandler;
import home.bot.service.CallbackQueryHandlerService;
import home.bot.service.MessageHandlerService;
import home.bot.service.telegram.KeyboardCreateService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("TelegramEventHandler")
@Slf4j(topic = "EVENT_HANDLER.TELEGRAM")
public class TelegramEventHandler extends AbstractEventHandler<Update, BotApiMethod<?>> {

  private KeyboardCreateService keyboardCreateService;
  private MessageHandlerService<Update, ?> messageHandlerService;
  private CallbackQueryHandlerService<Update> callbackQueryHandlerService;

  public TelegramEventHandler(KeyboardCreateService keyboardCreateService,
                              MessageHandlerService<Update, ?> messageHandlerService,
                              CallbackQueryHandlerService<Update> callbackQueryHandlerService) {
    this.keyboardCreateService = keyboardCreateService;
    this.messageHandlerService = messageHandlerService;
    this.callbackQueryHandlerService = callbackQueryHandlerService;
  }

  @Override
  public BotApiMethod<?> handleEvent(EventDto<Update> eventDto) {
    LOGGER.info("Start handle event from telegram. Event: {}", eventDto);
    Update event = eventDto.getEvent();
    EventMessageTgResponse response = processEvent(event);
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText(response.getResponseText());
    sendMessage.setChatId(response.getChatId());
    Optional.ofNullable(response.getActivityId())
        .map(KeyboardByActivity::receiveKeyboardByActivity)
        .ifPresent(keyboard -> keyboardCreateService.initKeyboard(sendMessage, keyboard));
    sendMessage.enableMarkdown(true);
    return sendMessage;
  }

  private EventMessageTgResponse processEvent(Update event) {
    EventMessage<Update> dto = new EventMessage<>();
    dto.setChatId(extractChatId(event));
    dto.setText(extractText(event));
    dto.setMessage(event);
    return (EventMessageTgResponse) (event.hasCallbackQuery() ? callbackQueryHandlerService.processCallbackQuery(dto)
            : messageHandlerService.processMessage(dto));
  }

  private String extractChatId(Update event) {
    return Optional.ofNullable(event)
        .filter(Update::hasCallbackQuery)
        .map(Update::getCallbackQuery)
        .map(CallbackQuery::getMessage)
        .map(Message::getChatId)
        .map(String::valueOf)
        .orElseGet(() -> String.valueOf(event.getMessage().getChatId()));
  }

  private String extractText(Update event) {
    return Optional.ofNullable(event)
        .filter(Update::hasCallbackQuery)
        .map(Update::getCallbackQuery)
        .map(CallbackQuery::getMessage)
        .map(Message::getText)
        .orElseGet(() -> event.getMessage().getText());
  }
}
