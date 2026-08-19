package de.muenchen.oss.appdash.backend.configuration.application;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public final class CacheConfiguration {
  public static final String REFERENCE_DATA = "referenceData";

  @Bean
  public CacheManager cacheManager() {
    final CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(REFERENCE_DATA);

    caffeineCacheManager.setCaffeine(
        Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .recordStats());

    return caffeineCacheManager;
  }
}
