package home.bot.entity;

import home.bot.dto.enums.NotificationSource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.CascadeType;
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
@Table(name = "NOTIFICATION_ADDRESS")
@GenericGenerator(
    name = "notification_address_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "NOTIFICATION_ADDRESS_SEQ")
    }
)
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
public class NotificationAddress extends BaseEntity<Long> {
  @Enumerated(EnumType.STRING)
  private NotificationSource source;

  private String address;

  private Boolean enable;

  private LocalTime notifyFrom = LocalTime.of(10, 0);

  private LocalTime notifyTo = LocalTime.of(19, 0);

  private LocalDateTime created;

  private LocalDateTime updated;

  @JoinColumn(name = "USER_ID")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private User user;

  @PrePersist
  public void prePersist() {
    this.created = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updated = LocalDateTime.now();
  }
}
