package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

public class OfferFacadeTestConfiguration {
    private final InMemoryFetcherTestImpl inMemoryFetcherTest;
    private final InMemoryOfferRepositoryTestImpl inMemoryOfferRepositoryTest;

    OfferFacadeTestConfiguration() {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(
                List.of(
                        new JobOfferResponse("Id1","Company1", "Title1", "23421", "https://example.com/1"),
                        new JobOfferResponse("Id2","Company2", "Title2", "23422", "https://example.com/2"),
                        new JobOfferResponse("Id3","Company3", "Title3", "23423", "https://example.com/3"),
                        new JobOfferResponse("Id4","Company4", "Title4", "23424", "https://example.com/4"),
                        new JobOfferResponse("Id5","Company5", "Title5", "23425", "https://example.com/5")
                )
        );
        this.inMemoryOfferRepositoryTest = new InMemoryOfferRepositoryTestImpl();
    }

    OfferFacadeTestConfiguration(List<JobOfferResponse> remoteClientOffers) {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(remoteClientOffers);
        this.inMemoryOfferRepositoryTest = new InMemoryOfferRepositoryTestImpl();
    }

    OfferFacade offerFacadeForTests(){
        return new OfferFacade(inMemoryOfferRepositoryTest, new OfferService(inMemoryOfferRepositoryTest,inMemoryFetcherTest));
    }
}
