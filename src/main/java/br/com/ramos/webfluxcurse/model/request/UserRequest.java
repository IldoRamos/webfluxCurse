package br.com.ramos.webfluxcurse.model.request;

public record UserRequest(
        String nome,
        String email,
        String password
) {
}
