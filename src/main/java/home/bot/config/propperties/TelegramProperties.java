package home.bot.config.propperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "telegram")
public class TelegramProperties {
  private String apiUrl;
  private String webhookPath;
  private String token;
  private String name;
}
