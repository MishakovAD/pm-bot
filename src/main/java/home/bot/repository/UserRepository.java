package home.bot.repository;

import home.bot.entity.NotificationAddress;
import home.bot.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  @EntityGraph(attributePaths = {"notificationAddresses"})
  Optional<User> findById(Long id);

  @EntityGraph(attributePaths = {"groups"})
  Optional<User> findByNotificationAddresses(NotificationAddress address);

  default User findByNotificationAddressesOrElseThrow(NotificationAddress address) {
    return findByNotificationAddresses(address)
        .orElseThrow(() -> new RuntimeException(String.format("User with address: %s and type: %s not found!", address.getAddress(), address.getSource())));
  }
}
