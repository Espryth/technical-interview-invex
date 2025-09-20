package me.javierca.interviews.invex.config.environment;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RequestLoggingHandler implements HandlerInterceptor {

  @Override
  public boolean preHandle(
      final @NotNull HttpServletRequest request,
      final @NotNull HttpServletResponse response,
      final @NotNull Object handler
  ) {
    log.info(
        "[SERVICE-REQUEST] Method: {}, Endpoint: {}, Headers: {}",
        request.getMethod(),
        request.getRequestURI(),
        Collections.list(request.getHeaderNames()).stream()
            .collect(Collectors.toMap(
                name -> name,
                request::getHeader
            ))
    );
    return true;
  }
}
