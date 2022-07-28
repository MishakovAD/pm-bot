package home.bot.service.telegram;

import home.bot.dto.enums.KeyboardByActivity;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface KeyboardCreateService {

  void initKeyboard(SendMessage message, KeyboardByActivity keyboard);

}
