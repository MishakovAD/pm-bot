package home.bot.dto.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum MessageHandlerType {

  START("startMessage", "/start"),
  ADMIN_BUTTONS_MATTERMOST("adminButtonsMessage", "/mm_custom_admin_buttons"), //slash command to admin buttons
  REGISTER_MATTERMOST_START("startRegisterMattermostMessage", "/mm_user_registration"), //slash command to start
  REGISTER_MATTERMOST_FINISH("finishRegisterMattermostMessage", "/finish_mm_user_registration"),

  RETURN_USER_POINTS_TYPES("returnUserPointsTypesMattermostMessage", "/return_mm_user_points_types"),
  FIND_USER_POINTS_BY_TYPE("findUserPointsMattermostMessage", "/find_mm_user_points"),

  CREATE_USER_POINTS_MATTERMOST_START("startMattermostUserPointsMessage", "/start_mm_user_points_create"),
  CREATE_USER_POINTS_MATTERMOST_FINISH("createMattermostUserPointsMessage", "/create_mm_user_points"),

  QUESTION_DONE_MATTERMOST_START("startQuestionDoneMessage", "/start_mm_question_done"),
  QUESTION_DONE_MATTERMOST_FINISH("finishQuestionDoneMessage", "/finish_mm_question_done"),

  ADD_COMMENT_TO_QUESTION_MATTERMOST_START("startAddCommentToQuestionMessage", "/start_add_mm_comment_to_question"),
  ADD_COMMENT_TO_QUESTION_MATTERMOST_FINISH("finishAddCommentToQuestionMessage", "/finish_add_mm_comment_to_question"),

  //tasks
  CREATE_USER_TASK_MATTERMOST_START("startMattermostCreateUserTaskMessage", "/start_mm_user_task_create"),
  CREATE_USER_TASK_MATTERMOST_FINISH("finishMattermostCreateUserTaskMessage", "/finish_mm_user_task_create"),
  REMOVE_USER_TASK_MATTERMOST_START("startMattermostRemoveUserTaskMessage", "/start_mm_user_task_remove"),
  REMOVE_USER_TASK_MATTERMOST_FINISH("finishMattermostRemoveUserTaskMessage", "/finish_mm_user_task_remove"),

  //admin commands
  ADD_USER_TO_GROUP_MATTERMOST_START("startAddUserToGroupMessage", "/start_add_mm_user_to_group"),
  ADD_USER_TO_GROUP_MATTERMOST_FINISH("finishAddUserToGroupMessage", "/finish_add_mm_user_to_group"),
  CREATE_GROUP_MATTERMOST_START("startCreateGroupMessage", "/start_mm_create_group"),
  CREATE_GROUP_MATTERMOST_FINISH("finishCreateGroupMessage", "/finish_mm_create_group"),
  DEFAULT("defaultMessageHandler", "/defaultMessageHandler");

  private final String value;
  private final String command;

  private static final Set<MessageHandlerType> CACHE = Stream.of(MessageHandlerType.values())
      .collect(Collectors.toSet());

  public static MessageHandlerType receiveValueByMessage(String message) {
    return CACHE.stream()
        .filter(c -> StringUtils.equals(message, c.getCommand()))
        .findFirst()
        .orElse(DEFAULT);
  }
}
