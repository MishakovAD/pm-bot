package home.bot.service.telegram.keyboards.impl;

import home.bot.service.telegram.keyboards.AbstractKeyboardCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@RequiredArgsConstructor
@Service("positionKeyboardHandler")
@Slf4j(topic = "KEYBOARD_SERVICE.POSITION_REPLY")
public class PositionKeyboard extends AbstractKeyboardCreator {

  @Override
  public void initKeyboard(SendMessage message) {
    KeyboardRow row1 = new KeyboardRow();
    row1.add(new KeyboardButton("Разработчик"));
    row1.add(new KeyboardButton("Аналитик"));
    KeyboardRow row2 = new KeyboardRow();
    row2.add(new KeyboardButton("Тестировщик"));
    row2.add(new KeyboardButton("Я еще не определился(-лась)"));
    List<KeyboardRow> keyboardRows = Arrays.asList(row1, row2);

    ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
    keyboard.setKeyboard(keyboardRows);
    keyboard.setSelective(true);
    keyboard.setResizeKeyboard(true);
    keyboard.setOneTimeKeyboard(true);

    message.setReplyMarkup(keyboard);
  }
}
