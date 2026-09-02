package de.muenchen.oss.appdash.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;

@SpringBootApplication
@EnableEnversRepositories
@ConfigurationPropertiesScan
@SuppressWarnings("PMD.UseUtilityClass")
public class AppDashApplication {
  /* package */ static void main(final String... args) {
    SpringApplication.run(AppDashApplication.class, args);
  }
}
