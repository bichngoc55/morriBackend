package com.jelwery.morri.Exception;

import java.util.List;

public class ValidationException extends RuntimeException{
    private List<String> errors;
    public ValidationException(List<String> errors) {
        super("Validation failed: " + String.join(", ", errors));
        this.errors = errors;
    }
    public ValidationException(String message) {
        super(message);
    }
    public List<String> getErrors() {
        return errors;
    }

}
