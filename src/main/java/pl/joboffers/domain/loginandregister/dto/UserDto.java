package pl.joboffers.domain.loginandregister.dto;


public record UserDto(
        String id,
        String password,
        String username
) {
}
