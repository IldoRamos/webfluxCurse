package br.com.ramos.webfluxcurse.controller;

import br.com.ramos.webfluxcurse.entity.User;
import br.com.ramos.webfluxcurse.mapper.UserMapper;
import br.com.ramos.webfluxcurse.model.request.UserRequest;
import br.com.ramos.webfluxcurse.model.response.UserResponse;
import br.com.ramos.webfluxcurse.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.assertj.core.api.junit.jupiter.SoftlyExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static reactor.core.publisher.Mono.just;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    private static final String USERS = "/users";
    @Autowired
    private WebTestClient client;
    @MockBean
    private UserService Service;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private MongoClient mongoClient;
    @Autowired
    private UserService service;
    @Autowired
    private WebTestClient webTestClient;


    private static final String NOME = "ildo", ID ="123", EMAIL="ildo@gmail.com", SENHA = "123";
    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test endpoint save with success")
    void testSaveWithSuccess() {
        UserRequest request = new UserRequest(NOME, EMAIL,SENHA);
        Mockito.when(service.save(any())).thenReturn(just(User.builder().build()));

        webTestClient.post().uri(USERS)
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        verify(service, times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save with bad request")
    void testSaveWithBadRequest() {
        UserRequest request = new UserRequest(NOME.concat(" "), EMAIL,SENHA);

        webTestClient.post().uri(USERS)
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()

                .jsonPath("$.path").isEqualTo(USERS)
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation Error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.erros[0].fieldName").isEqualTo("nome")
                .jsonPath("$.erros[0].message").isEqualTo("field connot have blank spaces at the beginning or end");
    }

    @Test
    @DisplayName("Test find by id endpoint with success")
    void testeFindByIdSuccess() {
        final var userResponse = new UserResponse(ID, NOME,EMAIL,SENHA);
        when(service.findById(anyString())).thenReturn(Mono.just(User.builder().build()));
        when(mapper.toRespose(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri(USERS + "/" +ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.nome").isEqualTo(NOME)
                .jsonPath("$.email").isEqualTo(EMAIL)
                .jsonPath("$.password").isEqualTo(SENHA);

        verify(service).findById(anyString());
        verify(mapper).toRespose(any(User.class));
    }

    @Test
    @DisplayName("Test find all endpoint with success")
    void testFindAllWithSuccess() {
        final var userResponse = new UserResponse(ID, NOME,EMAIL,SENHA);
        when(service.findAll()).thenReturn(Flux.just(User.builder().build()));
        when(mapper.toRespose(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri(USERS)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(ID)
                .jsonPath("$.[0].nome").isEqualTo(NOME)
                .jsonPath("$.[0].email").isEqualTo(EMAIL)
                .jsonPath("$.[0].password").isEqualTo(SENHA);

        verify(service).findAll();
        verify(mapper).toRespose(any(User.class));
    }

    @Test
    @DisplayName("Test Update endpoint with success")
    void update() {
        UserRequest request = new UserRequest(NOME, EMAIL,SENHA);
        final var userResponse = new UserResponse(ID, NOME,EMAIL,SENHA);

        when(service.update(anyString(),any(UserRequest.class))).thenReturn(just(User.builder().build()));
        when(mapper.toRespose(any(User.class))).thenReturn(userResponse);

        webTestClient.patch().uri(USERS + "/" +ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.nome").isEqualTo(NOME)
                .jsonPath("$.email").isEqualTo(EMAIL)
                .jsonPath("$.password").isEqualTo(SENHA);;

                verify(service).update(anyString(),any(UserRequest.class));
                verify(mapper).toRespose(any(User.class));
    }

    @Test
    @DisplayName("Test detele endpoint with success")
    void delete() {
        when(service.delete(anyString())).thenReturn(just(User.builder().build()));
        webTestClient.delete().uri(USERS + "/" +ID)
                .exchange()
                .expectStatus().isOk();

        verify(service).delete(anyString());
    }
}