package home.bot.config;

import home.bot.config.mattermost.CustomMattermostClient;
import home.bot.config.mattermost.InteractiveMattermostClient;
import home.bot.config.propperties.MattermostProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = MattermostProperties.class)
public class MattermostClientConfig {

  private final MattermostProperties properties;

  @Bean
  @Qualifier("mattermostClient")
  public InteractiveMattermostClient mattermostClient(RestTemplate mattermostRestTemplate) {
    return new CustomMattermostClient(properties.getHost(), properties.getBotToken(), mattermostRestTemplate);
  }

  @Bean
  @Qualifier("mattermostRestTemplate")
  public RestTemplate mattermostRestTemplate(ClientHttpRequestInterceptor interceptor) {
    return new RestTemplateBuilder()
        .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
        .interceptors(interceptor)
        .build();
  }

}
