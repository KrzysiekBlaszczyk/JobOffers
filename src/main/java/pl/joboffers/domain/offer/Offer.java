package pl.joboffers.domain.offer;

import lombok.Builder;

@Builder
record Offer(
        String id,
        String company,
        String jobTitle,
        String salary,
        String url
) {
}
