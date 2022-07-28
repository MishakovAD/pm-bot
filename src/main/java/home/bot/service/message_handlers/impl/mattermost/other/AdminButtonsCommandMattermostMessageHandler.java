package home.bot.service.message_handlers.impl.mattermost.other;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.EventMessage;
import home.bot.dto.EventMessageMattermostResponse;
import home.bot.dto.enums.Role;
import home.bot.dto.matermost.request.SlashCommandRequest;
import home.bot.entity.User;
import home.bot.service.UserRoleService;
import home.bot.service.UserService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import home.bot.service.message_handlers.impl.mattermost.AbstractMattermostMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("adminButtonsMessage")
@Slf4j(topic = "MESSAGE_HANDLER.ADMIN_BUTTONS")
public class AdminButtonsCommandMattermostMessageHandler<T, Y> extends
    AbstractMattermostMessageHandler<SlashCommandRequest, Y> {

  private final UserService userService;
  private final UserRoleService userRoleService;

  public AdminButtonsCommandMattermostMessageHandler(MattermostService mattermostService,
      InteractiveMattermostClient mattermostClient,
      MattermostElementBuilder mattermostElementBuilder,
      UserService userService,
      UserRoleService userRoleService) {
    super(mattermostService, mattermostClient, mattermostElementBuilder);
    this.userService = userService;
    this.userRoleService = userRoleService;
  }

  @Override
  public EventMessageMattermostResponse processMessage(EventMessage<SlashCommandRequest> message) {
    LOGGER.info("Start process message.");
    SlashCommandRequest request = message.getMessage();
    String userId = request.getUserId();
    checkUserExists(userId);
    checkUserPermissions(userId);
    mattermostService.sendAdminBotMenuToUser(userId);
    return null;
  }

  private void checkUserExists(String userId) {
    if (!userService.isUserExists(userId)) {
      throw new RuntimeException(String.format("User with mm_id: %s does not exists", userId));
    }
  }

  private void checkUserPermissions(String userId) {
    User user = userService.getUserByAddress(userId);
    if (!userRoleService.isUserHasRole(user.getId(), Role.ADMIN)) {
      mattermostService.sendMessageToUser(userId, "Кажется этот вопрос стоит обсудить с твоим руководителем. Недостаточно прав.");
      throw new RuntimeException(String.format("User with mm_id: %s does not have permissions!", userId));
    }
  }

}
