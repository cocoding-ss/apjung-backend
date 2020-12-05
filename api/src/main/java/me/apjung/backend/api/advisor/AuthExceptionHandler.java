package me.apjung.backend.api.advisor;

import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.dto.response.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class AuthExceptionHandler extends BaseExceptionHandler {
    public AuthExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedEmailException.class)
    public ErrorResponse duplicatedEmailException(DuplicatedEmailException exception) {
        final var message = Optional.ofNullable(exception.getMessage())
                .orElse(getDefaultMessage(exception));
        return new ErrorResponse(message);
    }
}