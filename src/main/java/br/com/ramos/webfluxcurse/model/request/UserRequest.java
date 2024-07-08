package br.com.ramos.webfluxcurse.model.request;

import br.com.ramos.webfluxcurse.validator.TrimgString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @TrimgString
        @NotBlank(message = "must not be null or empty")
        @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
        String nome,

        @TrimgString
        @Email(message = "Invalid email")
        @NotBlank(message = "must not be null or empty")
        String email,

        @TrimgString
        @NotBlank(message = "must not be null or empty")
        @Size(min = 3, max = 20, message = "must be between 3 and 20 characters")
        String password
) {
}
