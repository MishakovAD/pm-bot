package home.bot.config.interceptors;

import home.bot.config.LoggingFilterConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "INTERCEPTOR.REST_TEMPLATE")
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    request.getHeaders().add(LoggingFilterConfig.MDC_TRACE_ID, MDC.get(LoggingFilterConfig.MDC_TRACE_ID));
    traceRequest(request, body);
    ClientHttpResponse response = execution.execute(request, body);
    traceResponse(response);
    return response;
  }

  private void traceRequest(HttpRequest request, byte[] body) {
    LOGGER.info("Call URI: {}, header {}, method: {}, body: {}", request.getURI(), request.getHeaders().toString(), request.getMethod(), new String(body, StandardCharsets.UTF_8));
  }

  private void traceResponse(ClientHttpResponse response) {
    try {
      StringBuilder inputStringBuilder = new StringBuilder();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), Charset.defaultCharset()));
      String line = bufferedReader.readLine();
      while (line != null) {
        inputStringBuilder.append(line);
        inputStringBuilder.append('\n');
        line = bufferedReader.readLine();
      }
      LOGGER.info("Response: status code: {}, status text: {}, body: {} ", response.getStatusCode(), response.getStatusText(), inputStringBuilder.toString());
    } catch (Exception e) {
      LOGGER.error("Error during logging of the response", e);
    }
  }

}
