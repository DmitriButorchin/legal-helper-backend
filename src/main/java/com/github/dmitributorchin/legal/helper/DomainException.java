package com.github.dmitributorchin.legal.helper;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final JsonErrorSource errorSource;

    public DomainException(String message, JsonErrorSource errorSource) {
        super(message);
        this.errorSource = errorSource;
    }
}
