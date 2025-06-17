package pl.joboffers.domain.offer;

import org.junit.Test;
import pl.joboffers.domain.offer.dto.JobOfferResponse;
import pl.joboffers.domain.offer.dto.OfferDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class OfferFacadeTest {

    @Test
    public void shouldFetchOffersFromRemoteServerAndSaveAllOffersWhenRepositoryIsEmpty(){
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.getAllOffers()).hasSize(0);

        //when
        List<OfferResponseDto> result = offerFacade.findAllOffersAndSaveAllIfNotExists();

        //then
        assertThat(result).hasSize(5);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenOfferNotFound(){
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(offerFacade.getAllOffers()).hasSize(0);

        //when
        Throwable thrown = catchThrowable(() -> offerFacade.getOfferById("1000"));

        //then
        assertThat(thrown)
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id 1000 not found");
    }

    @Test
    public void shouldThrowDuplicateExceptionWhenOfferUrlExists(){
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferDto("test","Test", "Test", "1000", "TEST.PL"));
        assertThat(offerFacade.getOfferById(offerResponseDto.id())).isEqualTo(offerResponseDto);
        //when
        Throwable thrown = catchThrowable(() -> offerFacade.saveOffer(new OfferDto("Test","Test", "Test", "1000", "TEST.PL")));

        //then
        assertThat(thrown)
                .isInstanceOf(OfferDuplicateException.class)
                .hasMessage("Offer with url [TEST.PL] already exists");
    }

    @Test
    public void shouldThrowDuplicateExceptionWhenOfferIdExists() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferDto("1000","Test", "Test", "1000", "TEST.PL"));
        assertThat(offerFacade.getOfferById(offerResponseDto.id())).isEqualTo(offerResponseDto);
        //when
        Throwable thrown = catchThrowable(() -> offerFacade.saveOffer(new OfferDto("1000","Test", "Test", "1000", "TEST2.PL")));

        //then
        assertThat(thrown)
                .isInstanceOf(OfferSavingException.class)
                .hasMessage("Offer with id [1000] already exists");
    }

    @Test
    public void shouldFindOfferByIdWhenOfferWasSaved(){
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferDto("1", "Test", "Test", "1000", "TEST.PL"));
        assertThat(offerFacade.getOfferByUrl(offerResponseDto.url())).isEqualTo(offerResponseDto);

        //when
        OfferResponseDto result = offerFacade.getOfferById("1");

        //then
        assertThat(result).isEqualTo(offerResponseDto);
    }

    @Test
    public void shouldFindOfferByUrlWhenOfferWasSaved() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferDto("1", "Test", "Test", "1000", "TEST.PL"));
        assertThat(offerFacade.getOfferByUrl(offerResponseDto.url())).isEqualTo(offerResponseDto);

        //when
        OfferResponseDto result = offerFacade.getOfferByUrl("TEST.PL");

        //then
        assertThat(result).isEqualTo(offerResponseDto);
    }

    @Test
    public void shouldFindAllOffersWhenOffersWasSaved() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferDto("1", "Test", "Test", "1000", "TEST.PL"));
        OfferResponseDto offerResponseDto2 = offerFacade.saveOffer(new OfferDto("2", "Test", "Test", "1000", "TEST2.PL"));

        //when
        List<OfferResponseDto> result = offerFacade.getAllOffers();

        //then
        assertThat(result).size().isEqualTo(2);
        assertThat(List.of(result.get(0),
                result.get(1))).isEqualTo(List.of(offerResponseDto, offerResponseDto2));
    }

    @Test
    public void shouldSaveOnly2OfferWhenRepositoryHad2AddedWithOfferUrls(){
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(
                List.of(
                        new JobOfferResponse("Id1","Company1", "Title1", "23421", "https://example.com/1"),
                        new JobOfferResponse("Id2","Company2", "Title2", "23422", "https://example.com/2"),
                        new JobOfferResponse("Id3", "Company2", "Title2", "23422", "https://example.com/3"),
                        new JobOfferResponse("Id4", "Company2", "Title2", "23422", "https://example.com/4")))
                .offerFacadeForTests();
        offerFacade.saveOffer(new OfferDto("Id1", "Company1", "Title1", "23421", "https://example.com/1"));
        offerFacade.saveOffer(new OfferDto("Id2", "Company2", "Title2", "23422", "https://example.com/2"));

        //when
        List<OfferResponseDto> result = offerFacade.findAllOffersAndSaveAllIfNotExists();

        //then
        assertThat(result).hasSize(2);
        assertThat(List.of(result.get(0).url(),
                result.get(1).url())).isEqualTo(List.of("https://example.com/3", "https://example.com/4"));

    }
}
