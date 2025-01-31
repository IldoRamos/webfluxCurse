package br.com.ramos.webfluxcurse.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandarError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<FieldError>  erros = new ArrayList<>();
    public ValidationError(LocalDateTime timestamp, String path, Integer status, String error, String message) {
        super(timestamp, path, status, error, message);
    }

    public void  addError(String fieldName, String message) {
        this.erros.add(new FieldError(fieldName, message));
    }
    @Getter
    @AllArgsConstructor
    private static final class FieldError{
        private String fieldName;
        private String message;
    }
}
