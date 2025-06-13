package pl.joboffers.domain.offer;

import org.junit.Test;
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
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferDto("Test", "Test", "1000", "TEST.PL"));
        assertThat(offerFacade.getOfferById(offerResponseDto.id())).isEqualTo(offerResponseDto);
        //when
        Throwable thrown = catchThrowable(() -> offerFacade.saveOffer(new OfferDto("Test", "Test", "1000", "TEST.PL")));

        //then
        assertThat(thrown)
                .isInstanceOf(OfferDuplicateException.class)
                .hasMessage("Offer with url [TEST.PL] already exists");

    }




}
