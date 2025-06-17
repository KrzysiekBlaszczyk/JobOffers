package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.OfferDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferFacade {
    private final OfferRepository repository;
    private final OfferService service;

    public List<OfferResponseDto> getAllOffers() {
        return repository.getAllOffers().stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .collect(Collectors.toList());
    }

    public List<OfferResponseDto> findAllOffersAndSaveAllIfNotExists() {
        return service.fetchAllOffersAndSaveAllIfNotExists().stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .toList();
    }

    public OfferResponseDto getOfferById(String id) {
        return repository.getOfferById(id)
                .map(OfferMapper::mapFromOfferToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    public OfferResponseDto getOfferByUrl(String url) {
        return repository.getOfferByUrl(url)
                .map(OfferMapper::mapFromOfferToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(url));
    }

    public OfferResponseDto saveOffer(OfferDto offerDto) {
        final Offer offer = OfferMapper.mapFromOfferDtoToOffer(offerDto);
        final Offer save = repository.save(offer);
        return OfferMapper.mapFromOfferToOfferDto(save);
    }

}
