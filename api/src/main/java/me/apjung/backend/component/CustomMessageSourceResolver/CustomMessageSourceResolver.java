package me.apjung.backend.component.CustomMessageSourceResolver;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageSourceResolver {
    private final MessageSource validationMessageSource;
    private final MessageSource businessMessageSource;

    public CustomMessageSourceResolver(MessageSource validationMessageSource, MessageSource businessMessageSource) {
        this.validationMessageSource = validationMessageSource;
        this.businessMessageSource = businessMessageSource;
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
}
