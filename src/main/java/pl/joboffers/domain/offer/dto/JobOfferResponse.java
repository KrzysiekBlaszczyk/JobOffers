package pl.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record JobOfferResponse(
        String id,
        String company,
        String jobTitle,
        String salary,
        String url
) {
}
