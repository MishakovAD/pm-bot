package home.bot.config.propperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "mattermost")
public class MattermostProperties {
  private String host;
  private String botToken;
}
