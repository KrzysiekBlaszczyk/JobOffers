package pl.joboffers.domain.loginandregister;



import org.junit.jupiter.api.Test;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

public class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(new InMemoryLoginAndRegisterRepository());

    @Test
    public void shouldRegisterUser() {
        //given
        RegisterUserDto register = new RegisterUserDto("User1", "password");

        //when
        RegistrationResultDto result = loginAndRegisterFacade.registerUser(register);

        //then
        assertThat(result.created()).isEqualTo(true);
        assertThat(result.username()).isEqualTo("User1");
        assertThat(result.id()).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenUsernameAlreadyExists() {
        //given
        RegisterUserDto register = new RegisterUserDto("User1", "password");
        RegistrationResultDto result = loginAndRegisterFacade.registerUser(register);
        RegisterUserDto register2 = new RegisterUserDto("User1", "password");

        //when
        Throwable thrown = catchThrowable(() -> loginAndRegisterFacade.registerUser(register2));

        //then
        assertThat(thrown)
                .isInstanceOf(UsernameAlreadyExistsException.class)
                .hasMessage("Username already exists");
    }

    @Test
    public void shouldFindUserByUsername() {
        //given
        RegisterUserDto register = new RegisterUserDto("User1", "password");
        RegistrationResultDto result = loginAndRegisterFacade.registerUser(register);

        //when
        UserDto findUser = loginAndRegisterFacade.findUserByUsername(result.username());

        //then
        assertThat(findUser.id()).isEqualTo(result.id());
        assertThat(findUser.username()).isEqualTo(result.username());
        assertThat(findUser.password()).isEqualTo(register.password());
    }

    @Test
    public void shouldThrowExceptionWhenUsernameNotFound() {
        //given
        String username = "testUser";

        //when
        Throwable thrown =  catchThrowable(() -> loginAndRegisterFacade.findUserByUsername(username));

        //then
        assertThat(thrown)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }
}