package me.apjung.backend.api.advisor;

import me.apjung.backend.api.exception.ShopNotFoundException;
import me.apjung.backend.component.custom_message_source_resolver.CustomMessageSourceResolver;
import me.apjung.backend.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ShopExceptionHandler extends BaseExceptionHandler {
    public ShopExceptionHandler(CustomMessageSourceResolver customMessageSourceResolver) {
        super(customMessageSourceResolver);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShopNotFoundException.class)
    public ErrorResponse duplicatedEmailException(ShopNotFoundException exception) {
        return getErrorResponse(exception);
    }
}