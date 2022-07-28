package home.bot.dto.jira.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import home.bot.dto.jira.JiraUser;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue {
  private String id;
  private String key;
  private IssueFields fields;
}
