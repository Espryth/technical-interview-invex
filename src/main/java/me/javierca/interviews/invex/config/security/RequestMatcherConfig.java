package me.javierca.interviews.invex.config.security;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Component
public class RequestMatcherConfig implements RequestMatcher {

  private static final Set<String> PUBLIC_PATHS = Set.of(
      "/actuator/health",
      "/auth"
  );

  @Override
  public boolean matches(final HttpServletRequest request) {
    return PUBLIC_PATHS.contains(request.getRequestURI());
  }
}
