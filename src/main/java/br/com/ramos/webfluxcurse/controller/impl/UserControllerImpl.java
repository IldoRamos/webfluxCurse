package br.com.ramos.webfluxcurse.controller.impl;

import br.com.ramos.webfluxcurse.controller.UserController;
import br.com.ramos.webfluxcurse.mapper.UserMapper;
import br.com.ramos.webfluxcurse.model.request.UserRequest;
import br.com.ramos.webfluxcurse.model.response.UserResponse;
import br.com.ramos.webfluxcurse.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/{users}")
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @Override
    public ResponseEntity<Mono<Void>> save(@Valid UserRequest user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(user).then());
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> findById(String id) {

        return ResponseEntity.ok().body(
                userService.findById(id)
                        .map(userMapper::toRespose)
        );
    }

    @Override
    public ResponseEntity<Flux<UserResponse>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Mono<Void>> delete(String id) {
        return null;
    }
}
