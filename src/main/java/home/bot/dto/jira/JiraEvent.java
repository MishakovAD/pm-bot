package home.bot.dto.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import home.bot.dto.jira.changelog.JiraChangelog;
import home.bot.dto.jira.issue.JiraIssue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraEvent {
  private Long timestamp;
  private String webhookEvent;
  @JsonProperty("issue_event_type_name")
  private String issueEventTypeName; //TODO: узнать все типы и перенести в енам
  private JiraUser user;
  private JiraIssue issue;
  private JiraChangelog changelog;
}
