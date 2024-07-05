package br.com.ramos.webfluxcurse.model.response;

public record UserResponse(
        String id,
        String nome,
        String email,
        String password
) {
}
