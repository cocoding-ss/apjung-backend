package me.apjung.backend.api.advisor;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.api.exception.DuplicatedEmailException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public abstract class BaseExceptionHandler {
    protected static final String MESSAGE_KEY_PREFIX = "api.exception.";
    private final MessageSource messageSource;

    protected String getMessage(RuntimeException exception) {
        return this.getMessage(findCodeByRuntimeExceptionType(exception));
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    private String findCodeByRuntimeExceptionType(RuntimeException exception) {
        if (exception instanceof DuplicatedEmailException) {
            return MESSAGE_KEY_PREFIX + "DuplicatedEmailException.message";
        }
        return MESSAGE_KEY_PREFIX + "UndefinedException.message";
    }
}
