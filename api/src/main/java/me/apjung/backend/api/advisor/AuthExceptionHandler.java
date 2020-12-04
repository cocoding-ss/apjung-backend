package me.apjung.backend.api.advisor;

import me.apjung.backend.api.exception.DuplicatedEmailException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthExceptionHandler extends BaseExceptionHandler {
    public AuthExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedEmailException.class)
    public DuplicatedEmailException duplicatedEmailException(DuplicatedEmailException exception) {
        if (exception.getMessage() == null) {
            return exception;
        }
        return new DuplicatedEmailException(getMessage((exception)));
    }
}