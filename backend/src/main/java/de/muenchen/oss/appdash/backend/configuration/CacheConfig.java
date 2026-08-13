package de.muenchen.oss.appdash.backend.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
  public static final String REFERENCE_DATA = "referenceData";

  @Bean
  public CacheManager cacheManager() {
    final CaffeineCacheManager cacheManager = new CaffeineCacheManager(REFERENCE_DATA);

    cacheManager.setCaffeine(
        Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .recordStats());

    return cacheManager;
  }
}
