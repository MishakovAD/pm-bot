package home.bot.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import home.bot.dto.enums.PointType;
import home.bot.dto.enums.ProgressStatus;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Data
@Entity
@Table(name = "POINT")
@GenericGenerator(
    name = "event_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "POINT_SEQ")
    }
)
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonType.class),
    @TypeDef(name = "jsonb", typeClass = JsonType.class)
})
@ToString(exclude = {"questions"})
@EqualsAndHashCode(exclude = {"questions"})
public class Point extends BaseEntity<Long>{

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @JoinColumn(name = "POINT_ID")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    private LocalDateTime created;
    private LocalDateTime updated;

    @PrePersist
    public void prePersist() {
        this.status = ProgressStatus.IN_PROGRESS;
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
