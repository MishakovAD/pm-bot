package home.bot.service.impl;

import static home.bot.dto.enums.GroupPrivileges.GROUP_ADMIN;
import static home.bot.dto.enums.GroupPrivileges.GROUP_USER;

import home.bot.dto.CreateGroupDto;
import home.bot.dto.enums.GroupPrivileges;
import home.bot.entity.GroupPrivilege;
import home.bot.entity.NotificationAddress;
import home.bot.entity.User;
import home.bot.entity.UserGroup;
import home.bot.repository.UserGroupRepository;
import home.bot.service.NotificationAddressService;
import home.bot.service.UserGroupService;
import home.bot.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class UserGroupServiceImpl implements UserGroupService {

  private final UserService userService;
  private final UserGroupRepository userGroupRepository;
  private final NotificationAddressService notificationAddressService;

  @Override
  @Transactional
  public Optional<UserGroup> getGroupByAddress(NotificationAddress address) {
    return userGroupRepository.findByAddress(address);
  }

  @Override
  @Transactional
  public List<UserGroup> getGroupsByUser(String userAddress) {
    User user = userService.getUserByAddress(userAddress);
    return user.getGroups().stream().toList();
  }

  @Override
  @Transactional
  public void createGroup(CreateGroupDto dto, String adminId) {
    User user = userService.getUserByAddress(adminId);
    NotificationAddress address = notificationAddressService.addMattermostChannelId(dto.getChannelId());
    UserGroup group = new UserGroup();
    group.setAddress(address);
    group.setName(dto.getName());
    group.setEmail(dto.getEmail());
    group.setDescription(dto.getDescription());
    group.getUsers().add(user);
    GroupPrivilege privilege = createGroupPrivilege(user, group, GROUP_ADMIN);
    group.getGroupPrivileges().add(privilege);
    userGroupRepository.save(group);
  }

  @Override
  @Transactional
  public void addUserToGroup(Long userId, Long groupId) {
    User user = userService.getUserById(userId);
    UserGroup group = userGroupRepository.getById(groupId);
    group.getUsers().add(user);
    GroupPrivilege privilege = createGroupPrivilege(user, group, GROUP_USER);
    group.getGroupPrivileges().add(privilege);
    userGroupRepository.save(group);
  }

  @Override
  @Transactional
  public boolean isUserAlreadyRelatedToGroup(Long userId, Long groupId) {
    return userGroupRepository.getById(groupId)
        .getUsers()
        .stream()
        .anyMatch(user -> Objects.equals(user.getId(), userId));
  }

  private GroupPrivilege createGroupPrivilege(User user, UserGroup group, GroupPrivileges groupPrivilege) {
    GroupPrivilege privilege = new GroupPrivilege();
    privilege.setUser(user);
    privilege.setGroup(group);
    privilege.setPrivilege(groupPrivilege);
    return privilege;
  }

}
