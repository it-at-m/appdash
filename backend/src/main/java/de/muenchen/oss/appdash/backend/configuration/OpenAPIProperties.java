package de.muenchen.oss.appdash.backend.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "info.application")
@Data
@Validated
public class OpenAPIProperties {
  @NotNull private String name;

  @NotNull private String description;

  @NotNull private String version;
}
