package home.bot.service.telegram.impl;


import home.bot.dto.enums.KeyboardByActivity;
import home.bot.service.telegram.KeyboardCreateService;
import home.bot.service.telegram.keyboards.KeyboardCreator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class KeyboardCreateServiceImpl implements KeyboardCreateService {

  private final Map<String, KeyboardCreator> keyboardCreators;

  @Override
  public void initKeyboard(SendMessage message, KeyboardByActivity keyboard) {
    keyboardCreators.get(keyboard.getKeyboardHandler())
        .initKeyboard(message);
  }
}
