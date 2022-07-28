package home.bot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.bot.config.cached_body.CachedBodyHttpServletRequest;
import home.bot.config.filters.CamundaProcessEngineFilter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Configuration
@RequiredArgsConstructor
public class LoggingFilterConfig {

  public static final String MDC_TRACE_ID = "traceId";

  private final ObjectMapper mapper;

  private static final int MAX_PAYLOAD_LENGTH = 10000;

  @Bean
  public CommonsRequestLoggingFilter loggerRequestFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(Boolean.TRUE);
    filter.setIncludePayload(Boolean.TRUE);
    filter.setIncludeHeaders(Boolean.TRUE);
    filter.setIncludeClientInfo(Boolean.TRUE);
    filter.setMaxPayloadLength(MAX_PAYLOAD_LENGTH);
    return filter;
  }

  @Bean
  public FilterRegistrationBean loggingFilterRegistration(CommonsRequestLoggingFilter loggerRequestFilter) {
    FilterRegistrationBean registration = new FilterRegistrationBean(loggerRequestFilter);
    registration.addUrlPatterns("/mma/tacq/*");

    return registration;
  }

  @Bean
  public FilterRegistrationBean processEngineAuthenticationFilter() {
    FilterRegistrationBean<CamundaProcessEngineFilter> registration = new FilterRegistrationBean();
    registration.setName("camunda-auth");
    registration.setFilter(new CamundaProcessEngineFilter());
    registration.addInitParameter("authentication-provider",
        "org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider");
    registration.addUrlPatterns("/engine-rest/*", "/engine/*");
    return registration;
  }

  @Bean
  public CommonsResponseLoggingFilter loggerResponseFilter() {
    return new CommonsResponseLoggingFilter(mapper);
  }

  @Slf4j(topic = "REST")
  @RequiredArgsConstructor
  static class CommonsResponseLoggingFilter extends GenericFilterBean {

    private static final String POST = "POST";

    private final ObjectMapper mapper;

    @Override
    public void doFilter(ServletRequest request,
        ServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {
      MDC.put(MDC_TRACE_ID, UUID.randomUUID().toString());
      var requestWrapper = new CachedBodyHttpServletRequest((HttpServletRequest) request);
      var responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
      try {
        logRequest(requestWrapper);
        filterChain.doFilter(requestWrapper, responseWrapper);
      } catch (Exception e) {
        LOGGER.error(String.format("Error at rest method to path: %s", ((HttpServletRequest) request).getServletPath()), e);
        throw e;
      } finally {
        logResponse(requestWrapper, responseWrapper);
        responseWrapper.copyBodyToResponse();
        MDC.remove(MDC_TRACE_ID);
      }

    }

    @SneakyThrows
    private void logRequest(CachedBodyHttpServletRequest requestWrapper) {
      if (isAllowedLog(requestWrapper)) {
        LOGGER.info("Before Request: \n" +
                "url: {}, \n" +
                "params: {}, \n" +
                "method: {}, \n" +
                "body: {}",
            requestWrapper.getRequestURI(),
            getQueryString(requestWrapper),
            requestWrapper.getMethod(),
            getRequestBodyAsString(requestWrapper));
      }
    }

    private void logResponse(CachedBodyHttpServletRequest requestWrapper,
        ContentCachingResponseWrapper responseWrapper) throws IOException {
      if (isNeedSkipResponseBody(requestWrapper)) {
        LOGGER.info("After Request. Response: \n" +
                "url: {}, \n" +
                "method: {}, \n" +
                "body: {}",
            requestWrapper.getRequestURI(),
            requestWrapper.getMethod(),
            "All date contains in DB!");
        return;
      }
      if (isAllowedLog(requestWrapper)) {
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseString = new String(responseArray, StandardCharsets.UTF_8);
        LOGGER.info("After Request. Response: \n" +
                "url: {}, \n" +
                "method: {}, \n" +
                "body: {}",
            requestWrapper.getRequestURI(),
            requestWrapper.getMethod(),
            responseString);
      }

    }

    private boolean isAllowedLog(CachedBodyHttpServletRequest requestWrapper) {
      return !excludeFromFilter(requestWrapper.getServletPath()) &&
          !isOctetStream(requestWrapper.getContentType());
    }

    private boolean excludeFromFilter(String path) {
      PathMatcher pathMatcher = new AntPathMatcher();
      return pathMatcher.match("/swagger**", path) ||
          pathMatcher.match("/swagger-resources/**", path) ||
          pathMatcher.match("/v2/api-docs/**", path) ||
          pathMatcher.match("/webjars/**", path) ||
          pathMatcher.match("/health/**", path) ||
          pathMatcher.match("/actuator/**", path) ||
          pathMatcher.match("/resume/**", path) ||
          pathMatcher.match("/engine-rest/**", path) ||
          pathMatcher.match("/css/**", path) ||
          pathMatcher.match("/js/**", path) ||
          pathMatcher.match("/favicon.ico", path) ||
          pathMatcher.match("/pause/**", path);
    }

    private boolean isNeedSkipResponseBody(HttpServletRequest servletRequest) {
      return StringUtils.equals(servletRequest.getMethod(), "GET");
    }

    private boolean isOctetStream(String contentType) {
      return Objects.equals(contentType, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    private String getQueryString(CachedBodyHttpServletRequest requestWrapper) {
      return StringUtils.isBlank(requestWrapper.getQueryString()) ?
          StringUtils.EMPTY : URLDecoder.decode(requestWrapper.getQueryString(), StandardCharsets.UTF_8);
    }

    private String getRequestBodyAsString(CachedBodyHttpServletRequest requestWrapper) throws IOException {
      return requestWrapper.getReader()
          .lines()
          .collect(Collectors.joining(System.lineSeparator()));
    }

  }

}
