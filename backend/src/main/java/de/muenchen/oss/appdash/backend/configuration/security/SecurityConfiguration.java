package de.muenchen.oss.appdash.backend.configuration.security;

import de.muenchen.oss.appdash.backend.application.service.connector.s3.AppInsightS3Properties;
import de.muenchen.oss.appdash.backend.application.service.connector.s3.TogenS3Properties;
import de.muenchen.oss.appdash.backend.configuration.filter.ApiKeyAuthenticationFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * The central class for configuration of all security aspects. Configures all endpoints to require
 * authentication via access token. (except the Spring Boot Actuator endpoints) Additionally it
 * configures the use of role-based authorization (via application.yml) or {@link
 * KeycloakPermissionsAuthoritiesConverter} (when profile "keycloak-permissions" is set).
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Import(RestClientAutoConfiguration.class)
@Slf4j
public class SecurityConfiguration {
  private final Optional<KeycloakPermissionsAuthoritiesConverter>
      keycloakPermissionsAuthoritiesConverter;

  @Bean
  @Order(1)
  public SecurityFilterChain apiKeyFilterChain(
      final HttpSecurity http,
      final AppInsightS3Properties appInsightProperties,
      final TogenS3Properties togenProperties)
      throws Exception {
    // API key registration map for url paths
    final Map<String, String> pathApiKeyMap = new HashMap<>();
    pathApiKeyMap.put("/s3/togen", togenProperties.apikey());

    final List<RequestMatcher> matchers =
        pathApiKeyMap.keySet().stream()
            .map(path -> PathPatternRequestMatcher.withDefaults().matcher(path + "/**"))
            .collect(Collectors.toList());

    if (matchers.isEmpty()) {
      http.securityMatcher(new NegatedRequestMatcher(AnyRequestMatcher.INSTANCE));
      return http.build();
    }

    final RequestMatcher s3Matcher = new OrRequestMatcher(matchers);

    http.securityMatcher(s3Matcher)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(
            new ApiKeyAuthenticationFilter(pathApiKeyMap),
            UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers(
                        // allow access to /actuator/info
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/actuator/info"),
                        // allow access to /actuator/health for OpenShift
                        // Health Check
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/actuator/health"),
                        // allow access to /actuator/health/liveness for
                        // OpenShift Liveness Check
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/actuator/health/liveness"),
                        // allow access to /actuator/health/readiness for
                        // OpenShift Readiness Check
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/actuator/health/readiness"),
                        // allow access to opean-api endpoints
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/v3/api-docs"),
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/v3/api-docs.yaml"),
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/v3/api-docs/**"),
                        // allow access to swagger-ui
                        PathPatternRequestMatcher.withDefaults().matcher("/swagger-ui/**"),
                        // allow access to SBOM endpoints
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/actuator/sbom"),
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/actuator/sbom/application"),
                        // allow access to /actuator/metrics for Prometheus
                        // monitoring in OpenShift
                        PathPatternRequestMatcher.withDefaults()
                            .matcher(HttpMethod.GET, "/actuator/metrics"))
                    .permitAll())
        .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
        .oauth2ResourceServer(
            oAuth2ResourceServerConfigurer ->
                keycloakPermissionsAuthoritiesConverter.ifPresentOrElse(
                    converter ->
                        oAuth2ResourceServerConfigurer.jwt(
                            jwtConfigurer -> {
                              log.info(
                                  "Using permission-based"
                                      + " authorization. Start"
                                      + " without"
                                      + " 'keycloak-permissions'"
                                      + " profile to use"
                                      + " role-based"
                                      + " authorization.");
                              final JwtAuthenticationConverter jwtAuthenticationConverter =
                                  new JwtAuthenticationConverter();
                              jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                                  converter);
                              jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter);
                            }),
                    () -> {
                      log.info(
                          "Using role-based authorization. Start with"
                              + " 'keycloak-permissions' profile to use"
                              + " permission-based authorization.");
                      oAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults());
                    }));

    return http.build();
  }
}
