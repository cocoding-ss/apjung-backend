package me.apjung.backend.api.advisor;

import me.apjung.backend.api.exception.ShopFileUploadException;
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
    public ErrorResponse shopNotFoundException(ShopNotFoundException exception) {
        return getErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ShopFileUploadException.class)
    public ErrorResponse shopFileUploadException(ShopFileUploadException exception) {
        return getErrorResponse(exception);
    }
}