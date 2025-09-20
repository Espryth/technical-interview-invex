package me.javierca.interviews.invex.config.environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ResponseLoggingHandler implements ResponseBodyAdvice<Object> {

  private final ObjectMapper objectMapper;

  @Override
  public boolean supports(
      final @NotNull MethodParameter returnType,
      final @NotNull Class<? extends HttpMessageConverter<?>> converterType
  ) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
      final Object body,
      final @NotNull MethodParameter returnType,
      final @NotNull MediaType selectedContentType,
      final @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      final @NotNull ServerHttpRequest request,
      final @NotNull ServerHttpResponse response
  ) {
    final var status = response instanceof  ServletServerHttpResponse servletResponse
        ? servletResponse.getServletResponse().getStatus()
        : 200;
    log.info(
        "[SERVICE-RESPONSE] Endpoint: {}, Status: {}, Response: {}",
        status,
        request.getURI().getPath(),
        body == null ? "null" : this.objectMapper.valueToTree(body).toString()
    );
    return body;
  }
}
