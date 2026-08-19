package de.muenchen.oss.appdash.backend.application.service.connector.network;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

/*
 * NetworkService v2.0
 */
@Service
public class NetworkService {
  private final RestClient restClient;
  private final RestClient restClientWithProxy;

  public NetworkService(
      final RestClient restClient,
      @Qualifier("restClientWithProxy") final RestClient restClientWithProxy) {
    this.restClient = restClient;
    this.restClientWithProxy = restClientWithProxy;
  }

  // GET
  public <T> T get(final String url, final Class<T> responseType) {
    return get(url, responseType, null);
  }

  public <T> T get(
      final String url,
      final Class<T> responseType,
      final Consumer<RequestOptions> optionsConsumer) {
    return get(url, ParameterizedTypeReference.forType(responseType), optionsConsumer);
  }

  public <T> T get(
      final String url,
      final ParameterizedTypeReference<T> responseType,
      final Consumer<RequestOptions> optionsConsumer) {
    final RequestOptions options = buildOptions(optionsConsumer);
    return selectClient(options)
        .get()
        .uri(url)
        .headers(headers -> headers.addAll(options.getHeaders()))
        .retrieve()
        .body(responseType);
  }

  public <T> List<T> getList(
      final String url,
      final Class<T> elementClass,
      final Consumer<RequestOptions> optionsConsumer) {
    final ParameterizedTypeReference<List<T>> typeRef =
        ParameterizedTypeReference.forType(
            ResolvableType.forClassWithGenerics(List.class, elementClass).getType());
    return get(url, typeRef, optionsConsumer);
  }

  // POST
  public <T> T post(final String url, final Object body, final Class<T> responseType) {
    return post(url, body, responseType, null);
  }

  public <T> T post(
      final String url,
      final Object body,
      final Class<T> responseType,
      final Consumer<RequestOptions> optionsConsumer) {
    return post(url, body, ParameterizedTypeReference.forType(responseType), optionsConsumer);
  }

  public <T> T post(
      final String url,
      final Object body,
      final ParameterizedTypeReference<T> responseType,
      final Consumer<RequestOptions> optionsConsumer) {
    final RequestOptions options = buildOptions(optionsConsumer);
    final MediaType contentType =
        options.getHeaders().getContentType() != null
            ? options.getHeaders().getContentType()
            : MediaType.APPLICATION_JSON;

    return selectClient(options)
        .post()
        .uri(url)
        .headers(headers -> headers.addAll(options.getHeaders()))
        .contentType(contentType)
        .body(body)
        .retrieve()
        .body(responseType);
  }

  public <T> T postForm(
      final String url,
      final MultiValueMap<String, String> formData,
      final Class<T> responseType,
      final Consumer<RequestOptions> optionsConsumer) {
    return post(
        url,
        formData,
        ParameterizedTypeReference.forType(responseType),
        options -> {
          options.withContentType(MediaType.APPLICATION_FORM_URLENCODED);

          if (optionsConsumer != null) {
            optionsConsumer.accept(options);
          }
        });
  }

  // PUT
  public <T> T put(
      final String url,
      final Object body,
      final Class<T> responseType,
      final Consumer<RequestOptions> optionsConsumer) {
    final RequestOptions options = buildOptions(optionsConsumer);
    final MediaType contentType =
        options.getHeaders().getContentType() != null
            ? options.getHeaders().getContentType()
            : MediaType.APPLICATION_JSON;

    return selectClient(options)
        .put()
        .uri(url)
        .headers(headers -> headers.addAll(options.getHeaders()))
        .contentType(contentType)
        .body(body)
        .retrieve()
        .body(responseType);
  }

  // DELETE
  public void delete(final String url, final Consumer<RequestOptions> optionsConsumer) {
    final RequestOptions options = buildOptions(optionsConsumer);
    selectClient(options)
        .delete()
        .uri(url)
        .headers(headers -> headers.addAll(options.getHeaders()))
        .retrieve()
        .toBodilessEntity();
  }

  private RestClient selectClient(final RequestOptions options) {
    return options.isUseProxy() ? restClientWithProxy : restClient;
  }

  private RequestOptions buildOptions(final Consumer<RequestOptions> optionsConsumer) {
    final RequestOptions options = new RequestOptions();
    if (optionsConsumer != null) {
      optionsConsumer.accept(options);
    }
    return options;
  }

  @Getter
  public static class RequestOptions {
    private boolean useProxy = false;
    private final HttpHeaders headers = new HttpHeaders();

    public RequestOptions withProxy() {
      this.useProxy = true;
      return this;
    }

    public RequestOptions withBearerToken(final String token) {
      if (token != null && !token.isBlank()) {
        this.headers.setBearerAuth(token);
      }
      return this;
    }

    public RequestOptions withBasicAuth(final String username, final String password) {
      this.headers.setBasicAuth(username, password);
      return this;
    }

    public RequestOptions withHeader(final String headerName, final String headerValue) {
      this.headers.add(headerName, headerValue);
      return this;
    }

    public RequestOptions withContentType(final MediaType mediaType) {
      this.headers.setContentType(mediaType);
      return this;
    }

    public RequestOptions withAccept(final MediaType mediaType) {
      this.headers.setAccept(Collections.singletonList(mediaType));
      return this;
    }
  }
}
