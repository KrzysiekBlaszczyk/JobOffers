package pl.joboffers.feature;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.domain.offer.dto.JobOfferResponse;
import pl.joboffers.domain.offer.dto.OfferDto;

import java.util.List;

public class UserAuthorizedAndGetOffersIntegrationTest extends BaseIntegrationTest {
    @Autowired
    OfferFetchable offerFetchable;

    @Test
    public void user_register_and_authorized_and_get_offers(){
        // step 0: Stub external service to return job offers
        wireMockServer.stubFor(com.github.tomakehurst.wiremock.client.WireMock.get("/offer")
                .willReturn(com.github.tomakehurst.wiremock.client.WireMock.aResponse()
                        .withStatus(org.springframework.http.HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                    {
                                        "title": "Software Engineer",
                                        "company": "Google",
                                        "salary": "100k - 120k",
                                        "offerUrl": "https://google.com"
                                    },
                                    {
                                        "title": "Software Engineer",
                                        "company": "Amazon",
                                        "salary": "100k - 120k",
                                        "offerUrl": "https://amazon.com"
                                    }
                                ]
                                """)));

        List<JobOfferResponse> offerDto = offerFetchable.fetchOffers();

//        step1: User register to the system using POST /register with some username and some password
//        step2: User get token from created account using GET /getToken/{userID}
//        step3: User get offer by ID using GET /getOfferById/{offerID} with header "Authorization: {token}" and system returned OK(200) with offers
//        step4: System returns requested offer from database

    }
}
