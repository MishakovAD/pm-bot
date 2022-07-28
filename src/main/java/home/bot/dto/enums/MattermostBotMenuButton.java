package home.bot.dto.enums;

import static home.bot.controller.MattermostAdminCommandController.MATTERMOST_ADMIN_ADD_USER_TO_GROUP_START;
import static home.bot.controller.MattermostAdminCommandController.MATTERMOST_ADMIN_CREATE_GROUP_START;
import static home.bot.controller.MattermostPointController.MATTERMOST_ADD_COMMENT_TO_QUESTION_START;
import static home.bot.controller.MattermostPointController.MATTERMOST_USER_POINTS_DONE_START;
import static home.bot.controller.MattermostPointController.MATTERMOST_USER_POINTS_START;
import static home.bot.controller.MattermostTaskController.MATTERMOST_USER_TASK_CREATE_START;
import static home.bot.controller.MattermostUserController.MATTERMOST_USER_CURRENT_POINTS;
import static home.bot.controller.MattermostUserController.MATTERMOST_USER_POINTS_TYPES;
import static home.bot.dto.enums.ContextMapValuesBotMenuButtons.CONTEXT_MAP_KEY;
import static home.bot.dto.enums.ContextMapValuesBotMenuButtons.DAILY;
import static home.bot.dto.enums.ContextMapValuesBotMenuButtons.PERSONAL;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MattermostBotMenuButton {

  //admin buttons
  ADMIN_CREATE_GROUP_BUTTON("adminCreateGroup", "Создать группу.", MATTERMOST_ADMIN_CREATE_GROUP_START, Map.of()),
  ADMIN_ADD_USER_TO_GROUP_BUTTON("adminAddUserToGroup", "Добавить пользователя в группу.", MATTERMOST_ADMIN_ADD_USER_TO_GROUP_START, Map.of()),

  //user buttons
  USER_POINTS_START_BUTTON("userPointsCreate", "Добавить вопросов к обсуждению.", MATTERMOST_USER_POINTS_START, Map.of()),
  RETURN_POINTS_TYPES_BUTTON("returnPointsTypes", "Получить свои заметки.", MATTERMOST_USER_POINTS_TYPES, Map.of()),
  ADD_USER_TASK_BUTTON("createUserTask", "Создать \"задание\".", MATTERMOST_USER_TASK_CREATE_START, Map.of()),
  GET_DAILY_POINTS_BUTTON("getDailyPoints", "К дейли.", MATTERMOST_USER_CURRENT_POINTS, Map.of(CONTEXT_MAP_KEY, DAILY.getValue())),
  GET_PERSONAL_POINTS_BUTTON("getPersonalPoints", "Личные.", MATTERMOST_USER_CURRENT_POINTS, Map.of(CONTEXT_MAP_KEY, PERSONAL.getValue())),
  DONE_POINT_BUTTON("donePoint", "Закрыть вопрос. (Done)", MATTERMOST_USER_POINTS_DONE_START, Map.of()),
  ADD_NEW_QUESTIONS_TO_POINT_BUTTON("addQuestionToPoint", "Добавить еще вопросов.", MATTERMOST_USER_POINTS_START, Map.of(CONTEXT_MAP_KEY, "Don't edit original post!")),
  ADD_COMMENT_TO_QUESTION_BUTTON("addCommentToQuestion", "Добавить комментарий.",
      MATTERMOST_ADD_COMMENT_TO_QUESTION_START, Map.of());
  private final String id;
  private final String name;
  private final String url;
  private final Map<String, String> context;

  public static final List<MattermostBotMenuButton> MAIN_MENU_USER_BUTTONS = List.of(USER_POINTS_START_BUTTON,
      RETURN_POINTS_TYPES_BUTTON, ADD_USER_TASK_BUTTON);

  public static final List<MattermostBotMenuButton> POINTS_TYPES_USER_BUTTONS = List.of(GET_DAILY_POINTS_BUTTON,
      GET_PERSONAL_POINTS_BUTTON); //после нажатия RETURN_POINTS_TYPES_BUTTON -> возвращается весь список кнопок

  public static final List<MattermostBotMenuButton> POINTS_ACTIONS_USER_BUTTONS = List.of(DONE_POINT_BUTTON
//      ADD_NEW_QUESTIONS_TO_POINT_BUTTON, ADD_COMMENT_TO_QUESTION_BUTTON //todo: после MVP
  );

  public static final List<MattermostBotMenuButton> ADMIN_MENU_BUTTONS = List.of(ADMIN_CREATE_GROUP_BUTTON,
      ADMIN_ADD_USER_TO_GROUP_BUTTON
  );
}
