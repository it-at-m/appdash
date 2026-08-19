package de.muenchen.oss.appdash.backend.configuration.application;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Configuration
@EnableScheduling
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {
  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (throwable, method, params) -> {
      log.error(
          "Async error in method: {}() with arguments: {}",
          method.getName(),
          Arrays.toString(params),
          throwable);
    };
  }
}
