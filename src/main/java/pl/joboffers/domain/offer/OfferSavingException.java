package pl.joboffers.domain.offer;

import lombok.Getter;

import javax.management.RuntimeErrorException;
import java.util.List;

@Getter
public class OfferSavingException extends RuntimeException {
    private final List<String> offerIds;

    public OfferSavingException(String offerIds) {
        super(String.format("Offer with id [%s] already exists", offerIds));
        this.offerIds = List.of(offerIds);
    }

    public OfferSavingException(String message, List<Offer> offers) {
        super(String.format("error" + message + offers.toString()));
        this.offerIds = offers.stream()
                .map(Offer::url).toList();
    }
}
