package home.bot.entity;

import home.bot.dto.enums.ProgressStatus;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
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
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Entity
@Table(name = "QUESTION")
@GenericGenerator(
    name = "event_sequence_generator",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "QUESTION_SEQ")
    }
)
@NoArgsConstructor
@ToString(exclude = {"comments"})
@EqualsAndHashCode(exclude = {"comments"})
public class Question extends BaseEntity<Long>{

    private String text;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    @JoinColumn(name = "QUESTION_ID")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "POINT_ID", insertable = false, updatable = false)
    private Point point;

    public Question(String text) {
        this.status = ProgressStatus.IN_PROGRESS;
        this.text = text;
    }

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
