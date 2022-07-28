package home.bot.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventSource {
  TELEGRAM("TelegramEventHandler"),
  MATTERMOST_WEBHOOK("MattermostWebhookEventHandler"),
  MATTERMOST_SLASH_COMMAND("MattermostSlashCommandEventHandler"),
  MATTERMOST_USER_REGISTRATION("MattermostUserRegistrationEventHandler"),
  MATTERMOST_USER_POINTS_TYPES("MattermostGetUserPointsTypesEventHandler"),
  MATTERMOST_USER_CURRENT_POINTS("MattermostFindCurrentUserPointsEventHandler"),

  MATTERMOST_USER_POINT_CREATE_START("MattermostUserPointCreateStartEventHandler"),
  MATTERMOST_USER_POINT_CREATE_FINISH("MattermostUserPointCreateFinishEventHandler"),

  MATTERMOST_USER_QUESTION_DONE_START("MattermostUserQuestionDoneStartEventHandler"),
  MATTERMOST_USER_QUESTION_DONE_FINISH("MattermostUserQuestionDoneFinishEventHandler"),

  MATTERMOST_ADD_COMMENT_TO_QUESTION_START("MattermostAddCommentToQuestionStartEventHandler"),
  MATTERMOST_ADD_COMMENT_TO_QUESTION_FINISH("MattermostAddCommentToQuestionFinishEventHandler"),

  //tasks
  MATTERMOST_USER_TASK_CREATE_START("MattermostUserTaskCreateStartEventHandler"),
  MATTERMOST_USER_TASK_CREATE_FINISH("MattermostUserTaskCreateFinishEventHandler"),
  MATTERMOST_USER_TASK_REMOVE_START("MattermostUserTaskRemoveStartEventHandler"),
  MATTERMOST_USER_TASK_REMOVE_FINISH("MattermostUserTaskRemoveFinishEventHandler"),


  //admin handlers
  MATTERMOST_ADMIN_CREATE_GROUP_START("MattermostAdminCreateGroupStartEventHandler"),
  MATTERMOST_ADMIN_CREATE_GROUP_FINISH("MattermostAdminCreateGroupFinishEventHandler"),
  MATTERMOST_ADMIN_ADD_USER_TO_GROUP_START("MattermostAddUserToGroupStartEventHandler"),
  MATTERMOST_ADMIN_ADD_USER_TO_GROUP_FINISH("MattermostAddUserToGroupFinishEventHandler"),
  JIRA("JiraEventHandler"),
  EMAIL("EmailEventHandler");

  private final String source;
}
