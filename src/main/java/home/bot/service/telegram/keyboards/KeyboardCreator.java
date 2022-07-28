package home.bot.service.telegram.keyboards;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface KeyboardCreator {
  void initKeyboard(SendMessage message);
}
