package me.apjung.backend.api.advisor;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.component.custom_message_source_resolver.CustomMessageSourceResolver;
import me.apjung.backend.dto.response.ErrorResponse;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseExceptionHandler {
    protected final CustomMessageSourceResolver customMessageSourceResolver;

    protected ErrorResponse getErrorResponse(RuntimeException exception) {
        return new ErrorResponse(getMessage(exception));
    }

    private String getMessage(RuntimeException exception) {
        return Optional.ofNullable(exception.getMessage())
                .orElse(customMessageSourceResolver.getExceptionMessage(exception));
    }
}
