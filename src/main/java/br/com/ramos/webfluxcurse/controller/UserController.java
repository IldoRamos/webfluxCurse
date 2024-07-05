package br.com.ramos.webfluxcurse.controller;

import br.com.ramos.webfluxcurse.entity.User;
import br.com.ramos.webfluxcurse.model.request.UserRequest;
import br.com.ramos.webfluxcurse.model.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserController {
    @PostMapping
    ResponseEntity<Mono<Void>> save(@RequestBody UserRequest user);

    @GetMapping(value = "/{id}")
    ResponseEntity<Mono<UserResponse>> find(@PathVariable String id);

    @GetMapping
    ResponseEntity<Flux<UserResponse>> findAll();

    @PatchMapping(value = "/{id}")
    ResponseEntity<Mono<UserResponse>> update(@PathVariable String id, @RequestBody UserRequest request);
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Mono<Void>> delete(@PathVariable String id);

}
