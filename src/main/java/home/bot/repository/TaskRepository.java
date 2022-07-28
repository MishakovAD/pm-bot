package home.bot.repository;

import home.bot.dto.enums.TaskStatus;
import home.bot.entity.Task;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  @EntityGraph(attributePaths = {"user"})
  Optional<Task> findByIdAndStatus(Long id, TaskStatus status);

  @EntityGraph(attributePaths = {"user"})
  List<Task> findAllByExecutionDateAndStatus(LocalDate executionDate, TaskStatus status);

}
