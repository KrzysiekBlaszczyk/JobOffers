package pl.joboffers.domain.loginandregister.dto;

public record RegistrationResultDto(
        String id,
        boolean created,
        String username
) {
}
