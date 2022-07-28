package home.bot.entity;

import home.bot.dto.enum_converters.TaskTypeConverter;
import home.bot.dto.enums.TaskPeriod;
import home.bot.dto.enums.TaskStatus;
import home.bot.dto.enums.TaskType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Entity
@Table(name = "TASK")
@GenericGenerator(
    name = "task_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "TASK_SEQ")
    }
)
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
public class Task extends BaseEntity<Long> {

  @Column(unique = true) //todo: а точно ли нужна уникальность, может просто UUID?
  private String name;

  @JoinColumn(name = "USER_ID")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private User user;

  @Convert(converter = TaskTypeConverter.class)
  private TaskType type;

  @Enumerated(EnumType.STRING)
  private TaskStatus status;

  @Enumerated(EnumType.STRING)
  private TaskPeriod period;

  private LocalDate executionDate;

  private LocalTime executionTime = LocalTime.of(10, 15);

  private LocalDateTime created;

  private LocalDateTime updated;

  private byte[] body;

  @PrePersist
  public void prePersist() {
    this.created = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updated = LocalDateTime.now();
  }
}
