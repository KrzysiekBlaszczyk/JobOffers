package pl.joboffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferRepositoryTestImpl implements OfferRepository {

    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer entity) {
        if(database.values().stream().anyMatch(offer -> offer.url().equals(entity.url()))) {
            throw new OfferDuplicateException(entity.url());
        }
        UUID id = UUID.randomUUID();
        Offer offer = new Offer(id.toString(), entity.url(), entity.jobTitle(), entity.company(), entity.salary());
        database.put(id.toString(), offer);
        return offer;
    }

    @Override
    public List<Offer> getAllOffers() {
        return database.values().stream()
                .toList();
    }

    @Override
    public Optional<Offer> getOfferById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Offer> getOfferByUrl(String url) {
        return Optional.ofNullable(database.get(url));
    }

    @Override
    public boolean existsByOfferUrl(String url) {
        long count = database.values().stream()
                .filter(offer -> offer.url().equals(url))
                .count();
        return count == 1;
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
    }
}
