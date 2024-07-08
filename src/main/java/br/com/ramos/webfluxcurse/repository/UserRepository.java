package br.com.ramos.webfluxcurse.repository;

import br.com.ramos.webfluxcurse.entity.User;
import br.com.ramos.webfluxcurse.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Queue;

@Repository
@RequiredArgsConstructor
public class UserRepository{
    private final ReactiveMongoTemplate mongoTemplate;
    public Mono<User> save(User user){
        return mongoTemplate.save(user);
    }

    public Mono<User> findById(String id) {
        return  mongoTemplate.findById(id,User.class);
    }

    public Flux<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public Mono<User> findAndRemove(String id) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(id);
        return mongoTemplate.findAndRemove(query.addCriteria(criteria),User.class);
    }
}
