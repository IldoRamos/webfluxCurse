package br.com.ramos.webfluxcurse.controller.exceptions;

import br.com.ramos.webfluxcurse.service.exception.ObjectNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionsHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandarError>> duplicateKeyException(
            DuplicateKeyException ex, ServerHttpRequest request
    ) {
        return  ResponseEntity.badRequest()
                .body(Mono.just(
                        StandarError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .message(verifyDupKey(ex.getMessage()))
                                .path(request.getPath().toString())
                                .build()
                ));
    }
    @ExceptionHandler(WebExchangeBindException.class)
    ResponseEntity<Mono<ValidationError>> validationErrorException (
            WebExchangeBindException ex, ServerHttpRequest request
    ){
        ValidationError error = new ValidationError(
                LocalDateTime.now(),request.getPath().toString(),
                HttpStatus.BAD_REQUEST.value(),"Validation Error", "Error on validation attributes"
        );
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.just(error));
    }
    private String verifyDupKey(String message){
        if (message.contains("email dup key")){
            return "E-mail alread registered";
        }
        return "Duplicate Key exception";
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<Mono<StandarError>> objectNotFoundException(
            ObjectNotFoundException ex, ServerHttpRequest request
    ) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Mono.just(
                        StandarError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getPath().toString())
                                .build()
                ));
    }

}
