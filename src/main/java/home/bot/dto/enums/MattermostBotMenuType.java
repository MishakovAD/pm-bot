package home.bot.dto.enums;

import static home.bot.dto.enums.MattermostBotMenuButton.ADMIN_MENU_BUTTONS;
import static home.bot.dto.enums.MattermostBotMenuButton.MAIN_MENU_USER_BUTTONS;
import static home.bot.dto.enums.MattermostBotMenuButton.POINTS_TYPES_USER_BUTTONS;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MattermostBotMenuType {

  //Main menu
  FINISH_REGISTRATION("Спасибо за регистрацию! Смотри, что я умею!", MAIN_MENU_USER_BUTTONS),
  USER_ALREADY_REGISTERED("Мы с тобой уже знакомы, так что держи меню, заказывай!", MAIN_MENU_USER_BUTTONS),

//  ADMIN_MENU("Капитан, все в твоих руках!", ADMIN_MENU_BUTTONS), //todo: после MVP включить.

  ADMIN_MENU("Капитан, все в твоих руках!", Stream.of(ADMIN_MENU_BUTTONS, MAIN_MENU_USER_BUTTONS)
      .flatMap(Collection::stream)
      .toList()), //todo: remove after MVP

  //User flows after main
  GET_USER_POINTS_TYPES("Что именно интересует?", POINTS_TYPES_USER_BUTTONS);

  private final String message;
  private final List<MattermostBotMenuButton> buttons;
}
