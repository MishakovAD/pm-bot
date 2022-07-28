package home.bot.entity;

import home.bot.dto.enum_converters.PositionConverter;
import home.bot.dto.enums.Position;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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
@Table(name = "USER_TAB")
@GenericGenerator(
    name = "user_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
      @Parameter(name = "sequence_name", value = "USER_SEQ")
    }
)
@ToString(exclude = {"notificationAddresses", "points", "comments", "groupPrivileges", "tasks", "roles", "groups"})
@EqualsAndHashCode(exclude = {"notificationAddresses", "points", "comments", "groupPrivileges", "tasks", "roles", "groups"})
public class User extends BaseEntity<Long> {

  private String firstName;

  private String middleName;

  private String lastName;

  private LocalDate birthday;

  @Convert(converter = PositionConverter.class)
  private Position position;

  private LocalDateTime created;

  private LocalDateTime updated;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name="ROLE_ACCESS_TO_USER",
      joinColumns = @JoinColumn(name="USER_ID", referencedColumnName = "ID"),
      inverseJoinColumns = @JoinColumn(name="ROLE_ID", referencedColumnName = "ID")
  )
  private Set<RoleAccess> roles = new HashSet<>();

  @JoinColumn(name = "USER_ID")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<GroupPrivilege> groupPrivileges = new HashSet<>();

  @JoinColumn(name = "USER_ID")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<NotificationAddress> notificationAddresses = new HashSet<>(); //todo: перенести адрес_айди в юзера и через таблицу связку

  @JoinColumn(name = "USER_ID")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Point> points = new HashSet<>();

  @JoinColumn(name = "USER_ID")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Task> tasks = new HashSet<>();

  @JoinColumn(name = "USER_ID")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Comment> comments = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name="USER_GROUP_TO_USER",
      joinColumns = @JoinColumn(name="USER_ID", referencedColumnName = "ID"),
      inverseJoinColumns = @JoinColumn(name="GROUP_ID", referencedColumnName = "ID")
  )
  private Set<UserGroup> groups = new HashSet<>();

  @PrePersist
  public void prePersist() {
    this.created = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updated = LocalDateTime.now();
  }
}
