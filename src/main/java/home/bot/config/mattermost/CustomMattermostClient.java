package home.bot.config.mattermost;

import home.bot.dto.matermost.request.OpenDialogRequest;
import jakarta.ws.rs.core.GenericType;
import net.bis5.mattermost.client4.ApiResponse;
import net.bis5.mattermost.client4.MattermostClient;
import org.jboss.resteasy.specimpl.BuiltResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CustomMattermostClient extends MattermostClient implements InteractiveMattermostClient {

  private final RestTemplate restTemplate;

  private HttpHeaders headers;

  public CustomMattermostClient(String url, String botToken, RestTemplate restTemplate) {
    super(url);
    this.restTemplate = restTemplate;
    headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + botToken);
    headers.add("User-Agent", "tacq-mm_bot");
  }

  @Override
  public void openDialog(OpenDialogRequest request) {
    String url = "/actions/dialogs/open";
    doApiPost(url, request);
  }

  @Override
  protected <T, U> ApiResponse<T> doApiRequest(String method, String url, U data, String etag, Class<T> responseType) {
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.resolve(method), new HttpEntity<>(data, headers), responseType);
    return ApiResponse.of(new BuiltResponse(),response.getBody());
  }

  @Override
  protected <T, U> ApiResponse<T> doApiRequest(String method, String url, U data, String etag,
      GenericType<T> responseType) {
    ResponseEntity<T> response = (ResponseEntity<T>) restTemplate.exchange(url, HttpMethod.resolve(method), new HttpEntity<>(data, headers), responseType.getRawType());
    return ApiResponse.of(new BuiltResponse(),response.getBody());
  }

  @Override
  protected <U> ApiResponse<Void> doApiRequest(String method, String url, U data, String etag) {
    ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.resolve(method), new HttpEntity<>(data, headers), Void.class);
    return ApiResponse.of(new BuiltResponse(),response.getBody());
  }
}
