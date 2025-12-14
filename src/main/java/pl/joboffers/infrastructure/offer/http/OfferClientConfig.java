package pl.joboffers.infrastructure.offer.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offer.OfferFetchable;

import java.time.Duration;

@Configuration
public class OfferClientConfig {

    private final String uri;
    private final int port;
    private final int connectionTimeout;
    private final int readTimeout;

    public OfferClientConfig(
            @Value("${offer.http.client.config.uri}") String uri,
            @Value("${offer.http.client.config.port}") int port,
            @Value("${offer.http.client.config.connectionTimeout}") int connectionTimeout,
            @Value("${offer.http.client.config.readTimeout}") int readTimeout
    ) {
        this.uri = uri;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public OfferFetchable remoteOfferFetcher(RestTemplate restTemplate) {
        return new OfferRestTemplate(restTemplate, uri, port);
    }
}
