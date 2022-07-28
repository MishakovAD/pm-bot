package home.bot.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "USER_GROUP")
@GenericGenerator(
    name = "user_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
      @Parameter(name = "sequence_name", value = "USER_GROUP_SEQ")
    }
)
@ToString(exclude = {"users", "groupPrivileges"})
@EqualsAndHashCode(exclude = {"users", "groupPrivileges"})
public class UserGroup extends BaseEntity<Long> {

  @Column(unique = true)
  private String name;

  private String email;

  private String description;

  private LocalDateTime created;

  private LocalDateTime updated;

  @JoinColumn(name = "ADDRESS_ID")
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private NotificationAddress address;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name="USER_GROUP_TO_USER",
      joinColumns = @JoinColumn(name="GROUP_ID", referencedColumnName = "ID"),
      inverseJoinColumns = @JoinColumn(name="USER_ID", referencedColumnName = "ID")
  )
  private Set<User> users = new HashSet<>();

  @JoinColumn(name = "GROUP_ID")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<GroupPrivilege> groupPrivileges = new HashSet<>();

  @PrePersist
  public void prePersist() {
    this.created = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updated = LocalDateTime.now();
  }
}
