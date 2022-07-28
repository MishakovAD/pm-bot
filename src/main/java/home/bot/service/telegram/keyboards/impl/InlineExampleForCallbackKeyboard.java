package home.bot.service.telegram.keyboards.impl;

import home.bot.service.telegram.keyboards.AbstractKeyboardCreator;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@RequiredArgsConstructor
@Service("inlineexampleforcallback")
@Slf4j(topic = "KEYBOARD_SERVICE.EXAMPLE_INLINE")
public class InlineExampleForCallbackKeyboard extends AbstractKeyboardCreator {

  @Override
  public void initKeyboard(SendMessage message) {
    LOGGER.info("Start create main inline keyboard.");
    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
    rows.add(getButton("Ввести имя", "/setName"));
    keyboard.setKeyboard(rows);
    message.setReplyMarkup(keyboard);
  }

  private List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
    InlineKeyboardButton button = new InlineKeyboardButton();
    button.setText(buttonName);
    button.setCallbackData(buttonCallBackData);

    List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
    keyboardButtonsRow.add(button);
    return keyboardButtonsRow;
  }
}
