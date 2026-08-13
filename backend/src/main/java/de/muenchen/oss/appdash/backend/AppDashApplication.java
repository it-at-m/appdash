package de.muenchen.oss.appdash.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/** Application class for starting the microservice. */
@SpringBootApplication
@ConfigurationPropertiesScan
@SuppressWarnings("PMD.UseUtilityClass")
public class AppDashApplication {
  public static void main(final String[] args) {
    SpringApplication.run(AppDashApplication.class, args);
  }
}
