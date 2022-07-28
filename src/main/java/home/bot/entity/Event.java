package home.bot.entity;

import home.bot.dto.enums.EventSource;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Entity
@Table(name = "EVENT")
@GenericGenerator(
    name = "event_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "EVENT_SEQ")
    }
)
public class Event extends BaseEntity<Long> {

  @Enumerated(EnumType.STRING)
  private EventSource source;

  private byte[] body;

  private LocalDateTime created;

  @PrePersist
  public void prePersist() {
    this.created = LocalDateTime.now();
  }
}
