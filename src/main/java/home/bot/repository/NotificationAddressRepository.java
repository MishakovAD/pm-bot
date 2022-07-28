package home.bot.repository;

import home.bot.entity.NotificationAddress;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationAddressRepository extends JpaRepository<NotificationAddress, Long> {

  Optional<NotificationAddress> findByAddress(String address);

}
