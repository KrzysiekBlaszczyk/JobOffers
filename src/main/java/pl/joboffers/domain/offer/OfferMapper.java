package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponse;
import pl.joboffers.domain.offer.dto.OfferDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

public class OfferMapper {
    public static OfferResponseDto mapFromOfferToOfferDto(Offer offer){
        return OfferResponseDto.builder()
                .id(offer.id())
                .company(offer.company())
                .jobTitle(offer.jobTitle())
                .salary(offer.salary())
                .url(offer.url())
                .build();
    }

    public static Offer mapFromOfferDtoToOffer(OfferDto offerDto){
        return Offer.builder()
                .id(offerDto.id())
                .company(offerDto.company())
                .jobTitle(offerDto.jobTitle())
                .salary(offerDto.salary())
                .url(offerDto.url())
                .build();
    }

    public static Offer mapFromJobOfferResponseToOffer(JobOfferResponse jobOfferResponse){
        return Offer.builder()
                .id(jobOfferResponse.id())
                .company(jobOfferResponse.company())
                .jobTitle(jobOfferResponse.jobTitle())
                .salary(jobOfferResponse.salary())
                .url(jobOfferResponse.url())
                .build();
    }
}
