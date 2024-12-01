package com.jelwery.morri.Exception;

import org.springframework.http.HttpStatus;

public class CommonHttpStatusException extends RuntimeException {
private final HttpStatus httpStatus;
    private final String errorMessage;

    public CommonHttpStatusException(HttpStatus httpStatus, String errorMessage) {
        super(errorMessage);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
