package home.bot.service;

import home.bot.dto.enums.GroupPrivileges;
import home.bot.dto.enums.Role;

public interface UserRoleService {
  void addRoleToUser(Long userId, Role role);

  boolean isUserHasRole(Long userId, Role role);

  boolean isUserHasGroupPrivilege(Long userId, GroupPrivileges privilege);

}
