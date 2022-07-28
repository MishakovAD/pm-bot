package home.bot.service.telegram.keyboards.impl;

import home.bot.service.telegram.keyboards.AbstractKeyboardCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

@RequiredArgsConstructor
@Service("calendarKeyboardHandler")
@Slf4j(topic = "KEYBOARD_SERVICE.CALENDAR")
public class CalendarKeyboard extends AbstractKeyboardCreator {

  @Override
  public void initKeyboard(SendMessage message) {
    KeyboardRow row1 = new KeyboardRow();
    KeyboardButton calendar = new KeyboardButton("Календарь");
    WebAppInfo webAppInfo = new WebAppInfo();
    webAppInfo.setUrl("https://calendar.yoip.ru/2022-calendar.html");
    calendar.setWebApp(webAppInfo);
    row1.add(calendar);
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
  }
}
