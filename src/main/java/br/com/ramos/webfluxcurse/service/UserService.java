package br.com.ramos.webfluxcurse.service;

import br.com.ramos.webfluxcurse.entity.User;
import br.com.ramos.webfluxcurse.mapper.UserMapper;
import br.com.ramos.webfluxcurse.model.request.UserRequest;
import br.com.ramos.webfluxcurse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    public Mono<User> save(final UserRequest request) {
        return repository.save(mapper.toEntity(request));
    }

    public  Mono<User> findById(final String id) {
        return repository.findById(id);
    }
}
