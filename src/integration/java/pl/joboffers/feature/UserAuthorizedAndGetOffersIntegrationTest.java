package pl.joboffers.feature;


import org.junit.jupiter.api.Test;
import pl.joboffers.BaseIntegrationTest;

public class UserAuthorizedAndGetOffersIntegrationTest extends BaseIntegrationTest {
    @Test
    public void user_register_and_authorized_and_get_offers(){

//        step1: User register to the system using POST /register with some username and some password
//        step2: User get token from created account using GET /getToken/{userID}
//        step3: User get offer by ID using GET /getOfferById/{offerID} with header "Authorization: {token}" and system returned OK(200) with offers
//        step4: System returns requested offer from database

    }
}
