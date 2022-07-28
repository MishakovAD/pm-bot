package home.bot.entity;

import home.bot.dto.enums.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Entity
@Table(name = "ROLE_ACCESS")
@GenericGenerator(
    name = "role_access_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "ROLE_ACCESS_SEQ")
    }
)
@NoArgsConstructor
@AllArgsConstructor
public class RoleAccess extends BaseEntity<Long> {

  @Enumerated(EnumType.STRING)
  private Role role;

}
