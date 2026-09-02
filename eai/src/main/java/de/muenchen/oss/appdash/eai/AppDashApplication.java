package de.muenchen.oss.appdash.eai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings("PMD.UseUtilityClass")
public class AppDashApplication {
    /* package */ static void main(final String... args) {
        SpringApplication.run(AppDashApplication.class, args);
    }
}
