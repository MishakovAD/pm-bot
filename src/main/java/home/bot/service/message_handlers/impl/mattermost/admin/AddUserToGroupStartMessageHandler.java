package home.bot.service.message_handlers.impl.mattermost.admin;

import static home.bot.config.LoggingFilterConfig.MDC_TRACE_ID;
import static home.bot.controller.MattermostAdminCommandController.MATTERMOST_ADMIN_ADD_USER_TO_GROUP_FINISH;
import static home.bot.dto.enums.MattermostDialogElementValue.AVAILABLE_GROUPS_BY_USER;
import static home.bot.dto.enums.MattermostDialogElementValue.AVAILABLE_USERS_FOR_ADD_TO_GROUP;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.matermost.MattermostDialog;
import home.bot.dto.matermost.MattermostDialogElement;
import home.bot.dto.matermost.request.OpenDialogRequest;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.entity.UserGroup;
import home.bot.service.UserGroupService;
import home.bot.service.UserService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service("startAddUserToGroupMessage")
@Slf4j(topic = "MESSAGE_HANDLER.ADD_USER_TO_GROUP_MATTERMOST")
public class AddUserToGroupStartMessageHandler<T, Y> extends AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  private final UserService userService;
  private final UserGroupService userGroupService;

  public AddUserToGroupStartMessageHandler(UserService userService, UserGroupService userGroupService,
                                          MattermostService mattermostService,
                                          InteractiveMattermostClient mattermostClient,
                                          MattermostElementBuilder mattermostElementBuilder) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.userService = userService;
    this.userGroupService = userGroupService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    LOGGER.info("Start process message.");
    PressMattermostButtonRequest request = message.getMessage();
    String triggerId = request.getTriggerId();
    String userId = request.getUserId();
    mattermostClient.openDialog(createRegistrationRequest(triggerId,userId));
    return null;
  }

  private OpenDialogRequest createRegistrationRequest(String triggerId, String userAddress) {
    MattermostDialogElement groups = mattermostElementBuilder.createSelectDialogElement(AVAILABLE_GROUPS_BY_USER, createGroupsByUserMap(userAddress));
    MattermostDialogElement users = mattermostElementBuilder.createSelectDialogElement(AVAILABLE_USERS_FOR_ADD_TO_GROUP, createUsersMap(userAddress));
    return OpenDialogRequest.builder()
        .trigger_id(triggerId)
        .url(RESPONSE_HOST + MATTERMOST_ADMIN_ADD_USER_TO_GROUP_FINISH)
        .dialog(MattermostDialog.builder()
            .callback_id(MDC.get(MDC_TRACE_ID))
            .title("Выбери пользователя, которого хочешь добавить в группу.")
            .icon_url(null)
            .elements(List.of(groups, users))
            .notify_on_cancel(true)
            .build())
        .build();
  }

  private Map<String, String> createGroupsByUserMap(String userAddress) {
    return userGroupService.getGroupsByUser(userAddress)
        .stream()
        .collect(Collectors.toMap(UserGroup::getName, group -> String.valueOf(group.getId())));
  }

  private Map<String, String> createUsersMap(String userAddress) {
    return userService.getAllUsersForAddToGroup(userAddress)
        .stream()
        .collect(Collectors.toMap(user -> (user.getFirstName() + " " + user.getLastName()), user -> String.valueOf(user.getId())));
  }

}
