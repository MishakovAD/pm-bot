package home.bot.service.message_handlers.impl.mattermost.tasks;

import static home.bot.config.LoggingFilterConfig.MDC_TRACE_ID;
import static home.bot.controller.MattermostTaskController.MATTERMOST_USER_TASK_CREATE_FINISH;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_GROUP_VALUE;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_TASK_EXECUTION_DATE;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_TASK_EXECUTION_TIME;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_TASK_MESSAGE;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_TASK_NAME;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_TASK_PERIOD;
import static home.bot.dto.enums.MattermostDialogElementValue.USER_TASK_TYPE;
import static home.bot.dto.enums.TaskPeriod.EVERY_MONTH;
import static home.bot.dto.enums.TaskPeriod.EVERY_WEEK;
import static home.bot.dto.enums.TaskPeriod.EVERY_WORK_DAY;
import static home.bot.dto.enums.TaskPeriod.ONE_TIME;
import static home.bot.dto.enums.TaskType.ASK_ABOUT_PROGRESS;
import static home.bot.dto.enums.TaskType.MEETING_REMINDER;
import static home.bot.dto.enums.TaskType.NOTIFY;
import static home.bot.dto.enums.TaskType.POKER_PLANING;
import static home.bot.dto.enums.TaskType.TODO_LIST_NOTIFY;

import com.google.common.collect.Lists;
import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.enums.GroupPrivileges;
import home.bot.dto.enums.Role;
import home.bot.dto.enums.TaskPeriod;
import home.bot.dto.matermost.MattermostDialog;
import home.bot.dto.matermost.MattermostDialogElement;
import home.bot.dto.matermost.request.OpenDialogRequest;
import home.bot.dto.matermost.request.PressMattermostButtonRequest;
import home.bot.entity.User;
import home.bot.entity.UserGroup;
import home.bot.service.UserRoleService;
import home.bot.service.UserService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service("startMattermostCreateUserTaskMessage")
@Slf4j(topic = "MESSAGE_HANDLER.TASKS")
public class CreateUserTaskStartMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<PressMattermostButtonRequest, Y> {

  private UserService userService;
  private UserRoleService userRoleService;

  public CreateUserTaskStartMessageHandler(MattermostService mattermostService,
                                          InteractiveMattermostClient mattermostClient,
                                          MattermostElementBuilder mattermostElementBuilder,
                                          UserRoleService userRoleService,
                                          UserService userService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.userRoleService = userRoleService;
    this.userService = userService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<PressMattermostButtonRequest> message) {
    LOGGER.info("Start process message.");
    PressMattermostButtonRequest request = message.getMessage();
    String triggerId = request.getTriggerId();
    String userId = request.getUserId();
    mattermostClient.openDialog(createTaskRequest(triggerId, userId));
    return null;
  }

  private OpenDialogRequest createTaskRequest(String triggerId, String userAddress) {
    MattermostDialogElement name = mattermostElementBuilder.createDefaultDialogElement(USER_TASK_NAME);
    MattermostDialogElement type = mattermostElementBuilder.createSelectDialogElement(USER_TASK_TYPE, createTaskTypesMap(userAddress));
    MattermostDialogElement period = mattermostElementBuilder.createSelectDialogElement(USER_TASK_PERIOD, createTaskPeriodsMap());
    MattermostDialogElement date = mattermostElementBuilder.createDefaultDialogElement(USER_TASK_EXECUTION_DATE);
    MattermostDialogElement time = mattermostElementBuilder.createDefaultDialogElement(USER_TASK_EXECUTION_TIME);
    MattermostDialogElement message = mattermostElementBuilder.createDefaultDialogElement(USER_TASK_MESSAGE);
    List<MattermostDialogElement> elements = Lists.newArrayList(name, type, period, date, time, message);
    enrichElementsForGroups(elements, userAddress);
    return OpenDialogRequest.builder()
        .trigger_id(triggerId)
        .url(RESPONSE_HOST + MATTERMOST_USER_TASK_CREATE_FINISH)
        .dialog(MattermostDialog.builder()
            .callback_id(MDC.get(MDC_TRACE_ID))
            .title("Создание задания к выполнению.")
            .icon_url(null)
            .elements(elements)
            .notify_on_cancel(true)
            .build())
        .build();
  }

  private void enrichElementsForGroups(List<MattermostDialogElement> elements, String userAddress) {
    User user = userService.getUserByAddress(userAddress);
    if (user.getGroups().isEmpty()) {
      return;
    }
    boolean groupAdmin = userRoleService.isUserHasGroupPrivilege(user.getId(), GroupPrivileges.GROUP_ADMIN);
    if (!groupAdmin) {
      return;
    }
    MattermostDialogElement userGroups = mattermostElementBuilder.createSelectDialogElement(USER_GROUP_VALUE, createGroupsMap(user.getGroups()));
    elements.add(userGroups);
  }

  private Map<String, String> createTaskTypesMap(String userAddress) {
    Map<String, String> types = new LinkedHashMap<>();
    types.put(NOTIFY.getDescription(), NOTIFY.name());
    User user = userService.getUserByAddress(userAddress);
    if (userRoleService.isUserHasRole(user.getId(), Role.ADMIN) ||
        userRoleService.isUserHasGroupPrivilege(user.getId(), GroupPrivileges.GROUP_ADMIN)) {
      types.put(MEETING_REMINDER.getDescription(), MEETING_REMINDER.name());
      //todo: добавить опросник по вопросам для людей в группе (чтобы каждому написал и попросил заполнить)
//      types.put(ASK_ABOUT_PROGRESS.getDescription(), ASK_ABOUT_PROGRESS.name()); //todo: после MVP продумать концепцию
//      types.put(POKER_PLANING.getDescription(), POKER_PLANING.name());
//      types.put(TODO_LIST_NOTIFY.getDescription(), TODO_LIST_NOTIFY.name());
    }
    return types;
  }

  private Map<String, String> createTaskPeriodsMap() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put(ONE_TIME.getDescription(), ONE_TIME.name());
    map.put(EVERY_WORK_DAY.getDescription(), EVERY_WORK_DAY.name());
    map.put(EVERY_WEEK.getDescription(), EVERY_WEEK.name());
    map.put(EVERY_MONTH.getDescription(), EVERY_MONTH.name());
    return map;
  }

  private Map<String, String> createGroupsMap(Set<UserGroup> groups) {
    return groups.stream().collect(Collectors.toMap(UserGroup::getName, group -> String.valueOf(group.getId())));
  }

}
