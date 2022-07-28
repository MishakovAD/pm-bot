package home.bot.service.mattermost;

import home.bot.dto.UserRegistrationDto;
import home.bot.dto.enums.MattermostBotMenuType;
import home.bot.entity.User;
import net.bis5.mattermost.model.Post;

public interface MattermostService {
  User userRegistration(UserRegistrationDto userDto);

  void sendPointsTypesToUser(String userAddress, String channelId, String postId);

  void sendMainBotMenuToUser(String userId, MattermostBotMenuType type);

  void sendAdminBotMenuToUser(String userId);

  void sendMessageToUser(String userAddress, String message);
  void sendDirectMessageToUser(String directAddress, String message);

  void sendMessageFromBotToGroup(String address, String message);

  void editPost(Post updatePost);

  void deletePost(String postId);
}
