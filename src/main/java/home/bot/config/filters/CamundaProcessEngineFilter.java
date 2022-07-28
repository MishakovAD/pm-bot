package home.bot.config.filters;

import home.bot.config.cached_body.CachedBodyHttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.springframework.http.HttpHeaders;

@Slf4j(topic = "CAMUNDA_ADMIN")
public class CamundaProcessEngineFilter extends ProcessEngineAuthenticationFilter {

  private static final String NULL_USER = "null user";
  private static final String EXCAMAD_PING = "/engine-rest/engine";

  @Override
  @SneakyThrows
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    CachedBodyHttpServletRequest requestWrapper = new CachedBodyHttpServletRequest((HttpServletRequest) request);
    try {
      super.doFilter(requestWrapper, response, chain);
    } finally {
      if (!Objects.equals(EXCAMAD_PING, requestWrapper.getRequestURI())) {
        String headerAuth = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
        String user = getUserName(headerAuth);
        String requestBody = Objects.equals(requestWrapper.getMethod(), "POST")
            ? getRequestBodyAsString(requestWrapper)
            : Strings.EMPTY;
        LOGGER.info(">> request engine URL {}, user {}. Request body: {}", requestWrapper.getRequestURL().toString(), user, requestBody);
      }
    }
  }

  private String getUserName(String headerAuth) {
    try {
      return new String(Base64.getDecoder().decode(headerAuth.split(" ")[1])).split(":")[0];
    } catch (Exception ex) {
      LOGGER.info("Cannot calculate camunda user name: {}", ex.getMessage());
      return NULL_USER;
    }
  }

  private String getRequestBodyAsString(CachedBodyHttpServletRequest requestWrapper) throws IOException {
    return requestWrapper.getReader()
        .lines()
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
