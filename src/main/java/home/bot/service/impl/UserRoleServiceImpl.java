package home.bot.service.impl;

import home.bot.dto.enums.GroupPrivileges;
import home.bot.dto.enums.Role;
import home.bot.entity.GroupPrivilege;
import home.bot.entity.RoleAccess;
import home.bot.entity.User;
import home.bot.repository.UserRepository;
import home.bot.service.UserRoleService;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class UserRoleServiceImpl implements UserRoleService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public void addRoleToUser(Long userId, Role role) {
    User user = userRepository.getById(userId);
    user.getRoles().add(new RoleAccess(role));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public boolean isUserHasRole(Long userId, Role role) {
    User user = userRepository.getById(userId);
    return user.getRoles()
        .stream()
        .map(RoleAccess::getRole)
        .anyMatch(userRole -> Objects.equals(userRole, role));
  }

  @Override
  public boolean isUserHasGroupPrivilege(Long userId, GroupPrivileges privilege) {
    User user = userRepository.getById(userId);
    return user.getGroupPrivileges()
        .stream()
        .map(GroupPrivilege::getPrivilege)
        .anyMatch(userPrivilege -> Objects.equals(userPrivilege, privilege));
  }
}
