package pl.joboffers.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {
    Offer save(Offer offer);

    List<Offer> getAllOffers();

    Optional<Offer> getOfferById(String id);

    Optional<Offer> getOfferByUrl(String url);

    boolean existsByOfferUrl(String url);

    List<Offer> saveAll(List<Offer> offers);
}
