package pl.joboffers.infrastructure.offer.http;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.domain.offer.dto.JobOfferResponse;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class OfferRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;
    @Override
    public List<JobOfferResponse> fetchOffers() {
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            String urlForService = getUrlForService("/offer");
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
            ResponseEntity<List<JobOfferResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            final List<JobOfferResponse> body = response.getBody();
            if (body == null) {
                return Collections.emptyList();
            }
            return body;
        } catch (ResourceAccessException e) {
            return Collections.emptyList();
        }

    }
    private String getUrlForService(String path) {
        return uri + ":" + port + path;

    }
}
