package de.muenchen.oss.appdash.backend.configuration.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {
  private static final String API_KEY_HEADER = "X-API-Key";

  private final Map<String, String> pathApiKeyMap;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {

    final String path = request.getRequestURI();
    final String requestKey = request.getHeader(API_KEY_HEADER);

    if (requestKey == null || requestKey.isBlank()) {
      log.warn("Missing API key header (X-API-Key) in request to: {}", path);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing API Key");
      return;
    }

    String configuredKey = null;
    for (final Map.Entry<String, String> entry : pathApiKeyMap.entrySet()) {
      if (path.contains(entry.getKey())) {
        configuredKey = entry.getValue();
        break;
      }
    }

    if (configuredKey == null) {
      log.warn("No configured API key found matching request path: {}", path);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized path");
      return;
    }

    if (!configuredKey.equals(requestKey)) {
      log.warn("Invalid API key provided for path: {}", path);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
      return;
    }

    // Successfully authenticated
    final ApiKeyAuthenticationToken auth = new ApiKeyAuthenticationToken(requestKey);
    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(final HttpServletRequest request) {
    final String path = request.getRequestURI();
    return pathApiKeyMap.keySet().stream().noneMatch(path::contains);
  }

  private static class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private final String apiKey;

    public ApiKeyAuthenticationToken(final String apiKey) {
      super(List.of(new SimpleGrantedAuthority("ROLE_API_CLIENT")));
      this.apiKey = apiKey;
      setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
      return apiKey;
    }

    @Override
    public Object getPrincipal() {
      return "api-client";
    }
  }
}
