package home.bot.controller;

import home.bot.dto.Message;
import home.bot.dto.enums.EventSource;
import home.bot.service.EventHandlerService;
import home.bot.service.notification.impl.TelegramNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
public class TelegramWebhookController {

  private final EventHandlerService<Update, BotApiMethod<?>> eventHandlerService;

  @PostMapping("/")
  public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
    return eventHandlerService.handleEvent(EventSource.TELEGRAM, update);
  }


  private final TelegramNotificationService service;

  @PostMapping("/sendMessage")
  public Object sendMessage(Long userId, String text) {
    service.notify(Message.builder().message(text).build());
    return null;
  }

}
