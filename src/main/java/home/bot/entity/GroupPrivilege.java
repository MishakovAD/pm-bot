package home.bot.entity;

import home.bot.dto.enums.GroupPrivileges;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Entity
@Table(name = "GROUP_PRIVILEGE")
@GenericGenerator(
    name = "group_privilege_access_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "GROUP_PRIVILEGE_SEQ")
    }
)
@NoArgsConstructor
@AllArgsConstructor
public class GroupPrivilege extends BaseEntity<Long> {

  @Enumerated(EnumType.STRING)
  private GroupPrivileges privilege;

  @JoinColumn(name = "USER_ID")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private User user;

  @JoinColumn(name = "GROUP_ID")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private UserGroup group;

}
