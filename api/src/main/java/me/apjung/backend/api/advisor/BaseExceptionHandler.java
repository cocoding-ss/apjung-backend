package me.apjung.backend.api.advisor;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.component.CustomMessageSourceResolver.CustomMessageSourceResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RequiredArgsConstructor
public abstract class BaseExceptionHandler {
    protected final CustomMessageSourceResolver customMessageSourceResolver;
}
