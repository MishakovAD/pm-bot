package home.bot.repository;

import home.bot.entity.NotificationAddress;
import home.bot.entity.UserGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

  @EntityGraph(attributePaths = {"users"})
  Optional<UserGroup> findByAddress(NotificationAddress address);

}
