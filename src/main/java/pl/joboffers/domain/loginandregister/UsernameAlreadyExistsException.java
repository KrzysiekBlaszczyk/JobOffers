package pl.joboffers.domain.loginandregister;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String userExists) {
        super(userExists);
    }
}
