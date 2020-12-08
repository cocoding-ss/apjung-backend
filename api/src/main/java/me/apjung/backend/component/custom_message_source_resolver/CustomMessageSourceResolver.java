package me.apjung.backend.component.custom_message_source_resolver;

import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.api.exception.ShopNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageSourceResolver {
    private final MessageSource validationMessageSource;
    private final MessageSource businessMessageSource;
    private final MessageSource exceptionMessageSource;

    protected static final String BASE_MESSAGE_KEY_PREFIX = "api.exception.";
    protected static final String AUTH_MESSAGE_KEY_PREFIX = BASE_MESSAGE_KEY_PREFIX + "auth.";
    protected static final String SHOP_MESSAGE_KEY_PREFIX = BASE_MESSAGE_KEY_PREFIX + "shop.";

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
            return AUTH_MESSAGE_KEY_PREFIX + "DuplicatedEmailException.message";
        } else if (exception instanceof ShopNotFoundException) {
            return SHOP_MESSAGE_KEY_PREFIX + "ShopNotFoundException.message";
        }
        return BASE_MESSAGE_KEY_PREFIX + "UndefinedException.message";
    }
}
