package de.muenchen.oss.appdash.backend.application.service.connector.network;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

@Slf4j
public class HttpLoggingInterceptor implements ClientHttpRequestInterceptor {
  private static final int MAX_LOG_LENGTH = 10_000; // 10 KB limit

  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
      throws IOException {

    log.info("--> {} {}", request.getMethod(), request.getURI());

    if (body.length > 0 && isLoggableMediaType(request.getHeaders().getContentType())) {
      log.info("Request Body: {}", truncate(new String(body, StandardCharsets.UTF_8)));
    }

    final ClientHttpResponse response = execution.execute(request, body);
    final byte[] responseBody = StreamUtils.copyToByteArray(response.getBody());

    log.info("<-- {} (status: {})", request.getURI(), response.getStatusCode());

    if (responseBody.length > 0 && isLoggableMediaType(response.getHeaders().getContentType())) {
      log.info("Response Body: {}", truncate(new String(responseBody, StandardCharsets.UTF_8)));
    }

    return response;
  }

  private boolean isLoggableMediaType(final MediaType mediaType) {
    if (mediaType == null) {
      return false;
    }
    final String subtype = mediaType.getSubtype().toLowerCase(Locale.ROOT);
    return subtype.contains("json")
        || subtype.contains("xml")
        || subtype.contains("text")
        || subtype.contains("plain");
  }

  private String truncate(final String body) {
    if (body == null) return "";
    return body.length() > MAX_LOG_LENGTH
        ? body.substring(0, MAX_LOG_LENGTH) + " ... [TRUNCATED]"
        : body;
  }
}
