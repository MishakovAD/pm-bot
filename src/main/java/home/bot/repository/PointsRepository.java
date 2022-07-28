package home.bot.repository;

import home.bot.dto.enums.PointType;
import home.bot.dto.enums.ProgressStatus;
import home.bot.entity.Point;
import home.bot.entity.User;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsRepository extends JpaRepository<Point, Long> {

  Set<Point> findAllByUser(User user);

  Set<Point> findAllByUserAndPointType(User user, PointType pointType);

  Optional<Point> findByUserAndPointTypeAndStatusAndCreatedBefore(User user, PointType pointType, ProgressStatus status, LocalDateTime before);

}
