package me.apjung.backend.api.advisor;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.api.exception.DuplicatedEmailException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RequiredArgsConstructor
public abstract class BaseExceptionHandler {
    protected static final String MESSAGE_KEY_PREFIX = "api.exception.";
    private final MessageSource messageSource;

    protected String getDefaultMessage(RuntimeException exception) {
        return this.getDefaultMessage(findCodeByRuntimeExceptionType(exception));
    }

    private String getDefaultMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    private String findCodeByRuntimeExceptionType(RuntimeException exception) {
        if (exception instanceof DuplicatedEmailException) {
            return MESSAGE_KEY_PREFIX + "DuplicatedEmailException.message";
        }
        return MESSAGE_KEY_PREFIX + "UndefinedException.message";
    }
}
