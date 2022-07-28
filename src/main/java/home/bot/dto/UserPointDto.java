package home.bot.dto;

import home.bot.dto.enums.PointType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPointDto {
  private String userId;
  private PointType type;
  private Set<String> jiraTasks;
  private Set<String> openQuestions;
  private Set<String> otherProblems;
}
