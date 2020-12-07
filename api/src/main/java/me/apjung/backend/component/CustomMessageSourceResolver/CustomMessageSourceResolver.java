package me.apjung.backend.component.CustomMessageSourceResolver;

import me.apjung.backend.api.exception.DuplicatedEmailException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageSourceResolver {
    private final MessageSource validationMessageSource;
    private final MessageSource businessMessageSource;
    private final MessageSource exceptionMessageSource;

    protected static final String MESSAGE_KEY_PREFIX = "api.exception.";

    public CustomMessageSourceResolver(MessageSource validationMessageSource, MessageSource businessMessageSource, MessageSource exceptionMessageSource) {
        this.validationMessageSource = validationMessageSource;
        this.businessMessageSource = businessMessageSource;
        this.exceptionMessageSource = exceptionMessageSource;
    }

    public String getValidationMessage(String code) {
        return validationMessageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public String getValidationMessage(String code, Object [] args) {
        return validationMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public String getBusinessMessage(String code) {
        return businessMessageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public String getBusinessMessage(String code, Object [] args) {
        return businessMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public String getExceptionMessage(RuntimeException exception) {
        return this.getDefaultMessage(findCodeByRuntimeExceptionType(exception));
    }

    private String getDefaultMessage(String code) {
        return exceptionMessageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    private String findCodeByRuntimeExceptionType(RuntimeException exception) {
        if (exception instanceof DuplicatedEmailException) {
            return MESSAGE_KEY_PREFIX + "DuplicatedEmailException.message";
        }
        return MESSAGE_KEY_PREFIX + "UndefinedException.message";
    }
}
