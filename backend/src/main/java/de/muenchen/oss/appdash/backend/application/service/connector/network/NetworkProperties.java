package de.muenchen.oss.appdash.backend.application.service.connector.network;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "appcenter.network")
public record NetworkProperties(
    @DefaultValue("5s") Duration connectTimeout,
    @DefaultValue("10s") Duration readTimeout,
    LoggingProperties logging,
    ProxyProperties proxy) {

  public record LoggingProperties(@DefaultValue("false") boolean enabled) {}

  public record ProxyProperties(
      @DefaultValue("false") boolean enabled, String host, @DefaultValue("80") int port) {}
}
