package me.javierca.interviews.invex.config.environment;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class EnvironmentLoggingConfig implements WebMvcConfigurer {

  private final RequestLoggingHandler requestLoggingHandler;

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(this.requestLoggingHandler);
  }
}
