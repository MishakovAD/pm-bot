package home.bot.config;

import home.bot.config.propperties.TelegramProperties;
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
@EnableConfigurationProperties(value = TelegramProperties.class)
public class TelegramClientConfig {

  private final TelegramProperties telegramProperties;

  @Bean
  @Qualifier("telegramRestTemplate")
  public RestTemplate telegramRestTemplate(ClientHttpRequestInterceptor interceptor) {
    return new RestTemplateBuilder()
        .rootUri(rootUri())
        .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
        .interceptors(interceptor)
        .build();
  }

  private String rootUri() {
    return String.format("%sbot%s", telegramProperties.getApiUrl(), telegramProperties.getToken());
  }


}
