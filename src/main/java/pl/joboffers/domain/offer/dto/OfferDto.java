package pl.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(
        String company,
        String jobTitle,
        String salary,
        String url
) {
}
