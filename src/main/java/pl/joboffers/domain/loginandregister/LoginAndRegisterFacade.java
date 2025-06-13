package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import java.util.Optional;

@AllArgsConstructor
public class LoginAndRegisterFacade {
    private final LoginAndRegisterRepository repository;
    private static final String USER_NOT_FOUND = "User not found";
    private static final String USER_ALREADY_EXISTS = "Username already exists";

    public UserDto findUserByUsername(String username) {
        return repository.findUserByUsername(username)
                .map(user -> new UserDto(user.id(), user.password(), user.username()))
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public RegistrationResultDto registerUser(RegisterUserDto registerUserDto){
        if(repository.findUserByUsername(registerUserDto.username()).isPresent()){
            throw new UsernameAlreadyExistsException(USER_ALREADY_EXISTS);
        } else {
            final User user = User.builder()
                    .username(registerUserDto.username())
                    .password(registerUserDto.password())
                    .build();
            User savedUser = repository.save(user);
            return new RegistrationResultDto(savedUser.id(), true, savedUser.username());
        }
    }




}
