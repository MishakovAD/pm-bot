package home.bot.service.mattermost.impl;

import static home.bot.dto.enums.MattermostBotMenuType.ADMIN_MENU;
import static home.bot.dto.enums.MattermostBotMenuType.GET_USER_POINTS_TYPES;
import static home.bot.dto.enums.NotificationSource.MATTERMOST_CHANEL_ID;
import static home.bot.dto.enums.NotificationSource.MATTERMOST_DIRECT_ID;

import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.dto.UserRegistrationDto;
import home.bot.dto.enums.MattermostBotMenuType;
import home.bot.dto.enums.Role;
import home.bot.dto.matermost.MattermostNotifyAddressDto;
import home.bot.entity.NotificationAddress;
import home.bot.entity.User;
import home.bot.service.NotificationAddressService;
import home.bot.service.UserRegistrationService;
import home.bot.service.UserRoleService;
import home.bot.service.UserService;
import home.bot.service.mattermost.MattermostElementBuilder;
import home.bot.service.mattermost.MattermostService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bis5.mattermost.model.Bots;
import net.bis5.mattermost.model.Channel;
import net.bis5.mattermost.model.Post;
import net.bis5.mattermost.model.SlackAttachment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class MattermostServiceImpl implements MattermostService {

  @Value("${mattermost.bot-id}")
  protected String botId;

  private final UserService userService;
  private final UserRoleService userRoleService;
  private final InteractiveMattermostClient mattermostClient;
  private final UserRegistrationService userRegistrationService;
  private final MattermostElementBuilder mattermostElementBuilder;
  private final NotificationAddressService notificationAddressService;

  @Override
  public User userRegistration(UserRegistrationDto userDto) {
    User user = userRegistrationService.createUser(userDto);
    MattermostNotifyAddressDto notifyAddressDto = createMattermostNotifyAddressDto(userDto);
    notificationAddressService.addMattermostUserAddresses(user.getId(), notifyAddressDto);
    userRoleService.addRoleToUser(user.getId(), Role.USER);
    return user;
  }

  @Override
  public void sendPointsTypesToUser(String userAddress, String channelId, String postId) {
    Post post = createPostForDirectMessageBotMenuToUser(channelId, GET_USER_POINTS_TYPES);
    post.setId(postId);
    editPost(post);
  }

  @Override
  public void sendMainBotMenuToUser(String userId, MattermostBotMenuType type) {
    findDirectId(userId)
        .ifPresent(directId -> {
          Post post = createPostForDirectMessageBotMenuToUser(directId, type);
          sendPost(post);
        });
  }

  @Override
  public void sendAdminBotMenuToUser(String userId) {
    findDirectId(userId)
        .ifPresent(directId -> {
          Post post = createPostForDirectMessageBotMenuToUser(directId, ADMIN_MENU);
          sendPost(post);
        });
  }

  @Override
  public void sendMessageToUser(String userAddress, String message) {
    findDirectId(userAddress)
        .ifPresentOrElse(directId -> {
          mattermostClient.createPost(new Post(directId, message));
        }, () -> LOGGER.warn("Unknown direct id for user address: {}", userAddress));
  }

  @Override
  public void sendDirectMessageToUser(String directAddress, String message) {
    mattermostClient.createPost(new Post(directAddress, message));
  }

  @Override
  public void sendMessageFromBotToGroup(String chanelId, String message) {
    sendPost(new Post(chanelId, message));
  }

  @Override
  public void editPost(Post updatePost) {
    mattermostClient.updatePost(updatePost);
  }

  @Override
  public void deletePost(String postId) {
    mattermostClient.deletePost(postId);
  }

  private MattermostNotifyAddressDto createMattermostNotifyAddressDto(UserRegistrationDto userDto) {
    Channel directChanel = mattermostClient.createDirectChannel(botId, userDto.getChatId())
        .readEntity(); //botId + userId(chatId)
    return MattermostNotifyAddressDto.builder()
        .email(userDto.getEmail())
        .phone(userDto.getPhone())
        .chatId(userDto.getChatId())
        .dialogId(directChanel.getId())
        .build();
  }

  private Post createPostForDirectMessageBotMenuToUser(String channelId, MattermostBotMenuType type) {
    Post post = new Post();
    post.setChannelId(channelId);
    post.setMessage(type.getMessage());
    List<SlackAttachment> attachments = type.getButtons()
        .stream()
        .map(mattermostElementBuilder::createAttachment)
        .toList();
    post.setProps(Map.of("attachments", attachments));
    return post;
  }

  private Optional<String> findDirectId(String userAddress) {
    return userService.getUserByAddress(userAddress)
        .getNotificationAddresses()
        .stream()
        .filter(address -> Objects.equals(address.getSource(), MATTERMOST_DIRECT_ID))
        .map(NotificationAddress::getAddress)
        .findAny();
  }

  private void sendPost(Post post) {
    Post response = mattermostClient.createPost(post).readEntity();
    LOGGER.info("Post was successfully created! Post: {}", response);
  }
}
