package de.muenchen.oss.appdash.backend.application.service.connector.network;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class NetworkConfiguration {
  private final NetworkProperties networkProperties;

  public NetworkConfiguration(final NetworkProperties networkProperties) {
    this.networkProperties = networkProperties;
  }

  @Bean
  @ConditionalOnProperty(
      prefix = "appcenter.network.logging",
      name = "enabled",
      havingValue = "true")
  public ClientHttpRequestInterceptor loggingInterceptor() {
    return new HttpLoggingInterceptor();
  }

  @Bean
  @Primary
  public RestClient restClient(
      final RestClient.Builder builder, final List<ClientHttpRequestInterceptor> interceptors) {
    return builder
        .requestFactory(createRequestFactory(false))
        .requestInterceptors(list -> list.addAll(interceptors))
        .build();
  }

  @Bean
  public RestClient restClientWithProxy(
      final RestClient.Builder builder, final List<ClientHttpRequestInterceptor> interceptors) {
    return builder
        .requestFactory(createRequestFactory(true))
        .requestInterceptors(list -> list.addAll(interceptors))
        .build();
  }

  private ClientHttpRequestFactory createRequestFactory(final boolean useProxy) {
    final HttpClient.Builder clientBuilder =
        HttpClient.newBuilder().connectTimeout(networkProperties.connectTimeout());

    if (useProxy && networkProperties.proxy() != null && networkProperties.proxy().enabled()) {
      clientBuilder.proxy(
          ProxySelector.of(
              new InetSocketAddress(
                  networkProperties.proxy().host(), networkProperties.proxy().port())));
    }

    final JdkClientHttpRequestFactory factory =
        new JdkClientHttpRequestFactory(clientBuilder.build());
    factory.setReadTimeout(networkProperties.readTimeout());

    if (networkProperties.logging() != null && networkProperties.logging().enabled()) {
      return new BufferingClientHttpRequestFactory(factory);
    }

    return factory;
  }
}
