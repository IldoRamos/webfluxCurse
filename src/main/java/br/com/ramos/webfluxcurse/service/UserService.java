package br.com.ramos.webfluxcurse.service;

import br.com.ramos.webfluxcurse.entity.User;
import br.com.ramos.webfluxcurse.mapper.UserMapper;
import br.com.ramos.webfluxcurse.model.request.UserRequest;
import br.com.ramos.webfluxcurse.repository.UserRepository;
import br.com.ramos.webfluxcurse.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    public Mono<User> save(final UserRequest request) {
        return repository.save(mapper.toEntity(request));
    }

    public  Mono<User> findById(final String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ObjectNotFoundException("" +
                                format("Object not found. Id: %s, Type: %s",id, User.class))

                ));
    }

    public Flux<User> findAll(){
        return  repository.findAll();
    }

    public  Mono<User> update(final String id, final UserRequest request) {
        return repository.findById(id)
                .map(entity->mapper.toEntity(request,entity))
                .flatMap(repository::save);
    }
}
