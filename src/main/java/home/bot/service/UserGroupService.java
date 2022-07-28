package home.bot.service;

import home.bot.dto.CreateGroupDto;
import home.bot.entity.NotificationAddress;
import home.bot.entity.UserGroup;
import java.util.List;
import java.util.Optional;

public interface UserGroupService {

  Optional<UserGroup> getGroupByAddress(NotificationAddress address);
  List<UserGroup> getGroupsByUser(String userAddress);

  void createGroup(CreateGroupDto dto, String adminId);

  void addUserToGroup(Long userId, Long groupId);

  boolean isUserAlreadyRelatedToGroup(Long userId, Long groupId);
}
