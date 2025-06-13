package pl.joboffers.domain.loginandregister;

import java.util.Optional;

public interface LoginAndRegisterRepository {

    Optional<User> findUserByUsername(String username);
    User save(User user);
}
