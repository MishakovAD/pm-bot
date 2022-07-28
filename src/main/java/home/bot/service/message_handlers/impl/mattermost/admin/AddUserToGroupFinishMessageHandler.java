package home.bot.service.message_handlers.impl.mattermost.admin;

import static home.bot.dto.enums.MattermostDialogElementValue.MM_ADD_USER_TO_GROUP_MAPPING;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.AddUserToGroupDto;
import home.bot.dto.CreateBirthTaskDto;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.enums.NotificationSource;
import home.bot.dto.matermost.request.BaseMattermostSubmitDialogRequest;
import home.bot.entity.User;
import home.bot.entity.UserGroup;
import home.bot.service.TaskService;
import home.bot.service.UserGroupService;
import home.bot.service.UserService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("finishAddUserToGroupMessage")
@Slf4j(topic = "MESSAGE_HANDLER.ADD_USER_TO_GROUP_MATTERMOST")
public class AddUserToGroupFinishMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<BaseMattermostSubmitDialogRequest, Y> {

  private static final String TASK_BIRTH_NAME_PATTERN = "%s-%s_BIRTH_REMINDER";

  private final UserService userService;
  private final TaskService taskService;
  private final UserGroupService userGroupService;

  public AddUserToGroupFinishMessageHandler(MattermostService mattermostService,
      InteractiveMattermostClient mattermostClient,
      MattermostElementBuilder mattermostElementBuilder,
      UserGroupService userGroupService,
      TaskService taskService,
      UserService userService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.userGroupService = userGroupService;
    this.taskService = taskService;
    this.userService = userService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<BaseMattermostSubmitDialogRequest> message) {
    LOGGER.info("Start process message.");
    BaseMattermostSubmitDialogRequest request = message.getMessage();
    Map<String, Object> submission = request.getSubmission();
    AddUserToGroupDto dto = new AddUserToGroupDto();
    submission.entrySet()
        .stream()
        .filter(entry -> Objects.nonNull(entry.getValue()))
        .forEach(entry -> {
          MM_ADD_USER_TO_GROUP_MAPPING.get(entry.getKey()).accept(dto, String.valueOf(entry.getValue()));
        });

    checkRelatedUserToGroup(dto, request.getUserId());
    createBirthReminder(request.getUserId(), dto.getUserId());

    userGroupService.addUserToGroup(dto.getUserId(), dto.getGroupId());

    sendMessage(request.getUserId(), "Пользователь успешно добавлен в группу!");
    return null;
  }

  private void createBirthReminder(String adminAddress, Long userId) {
    User admin = userService.getUserByAddress(adminAddress);
    User user = userService.getUserById(userId);
    admin.getGroups()
        .stream()
        .map(UserGroup::getUsers)
        .flatMap(Collection::stream)
        .filter(member -> Objects.equals(member.getId(), user.getId()))
        .findAny()
        .ifPresentOrElse(
            member -> LOGGER.info("User with id: {} already member one of the groups admin: {}", member.getId(),
                admin.getId()),
            () -> {
              CreateBirthTaskDto taskDto = new CreateBirthTaskDto(NotificationSource.MATTERMOST_DIRECT_ID, user);
              taskDto.setName(extractTaskName(user));
              taskService.createTask(admin.getId(), taskDto);
            });
  }

  private String extractTaskName(User user) {
    return String.format(TASK_BIRTH_NAME_PATTERN, user.getFirstName(), user.getLastName());
  }

  private void checkRelatedUserToGroup(AddUserToGroupDto dto, String userAddress) {
    if (userGroupService.isUserAlreadyRelatedToGroup(dto.getUserId(), dto.getGroupId())) {
      sendMessage(userAddress, "Пользователь уже есть в этой группе!");
      throw new RuntimeException("User already in group!");
    }
  }

  private void sendMessage(String userAddress, String message) {
    mattermostService.sendMessageToUser(userAddress, message);
  }
}
