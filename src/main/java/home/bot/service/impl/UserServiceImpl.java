package home.bot.service.impl;

import home.bot.entity.NotificationAddress;
import home.bot.entity.User;
import home.bot.repository.NotificationAddressRepository;
import home.bot.repository.UserRepository;
import home.bot.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final NotificationAddressRepository notificationAddressRepository;

  @Override
  @Transactional
  public List<User> getAllUsersForAddToGroup(String userAdminAddress) {
    User admin = getUserByAddress(userAdminAddress);
    return userRepository.findAll()
        .stream()
        .filter(user -> !Objects.equals(user.getId(), admin.getId()))
        .toList();
  }

  @Override
  public User getUserById(Long userId) {
    return userRepository.getById(userId);
  }

  @Override
  @Transactional
  public User getUserByAddress(String userAddress) {
    return notificationAddressRepository.findByAddress(userAddress)
        .map(userRepository::findByNotificationAddressesOrElseThrow)
        .orElseThrow(() -> new RuntimeException(String.format("Unknown user by address: %s", userAddress)));
  }

  @Override
  public boolean isUserExists(String userAddress) {
    return notificationAddressRepository.findByAddress(userAddress)
        .map(userRepository::findByNotificationAddresses)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .isPresent();
  }
}
