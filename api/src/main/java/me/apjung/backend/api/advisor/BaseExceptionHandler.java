package me.apjung.backend.api.advisor;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.component.custom_message_source_resolver.CustomMessageSourceResolver;

@RequiredArgsConstructor
public abstract class BaseExceptionHandler {
    protected final CustomMessageSourceResolver customMessageSourceResolver;
}
