package com.github.dmitributorchin.legal.helper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    var errorSource = new JsonErrorSource("/" + fieldError.getObjectName() + "/" + fieldError.getField());
                    return new JsonError(fieldError.getDefaultMessage(), errorSource);
                })
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new JsonResponse(errors));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<JsonResponse> handleDomainErrors(DomainException ex) {
        var error = new JsonError(ex.getMessage(), ex.getErrorSource());
        return ResponseEntity.badRequest().body(new JsonResponse(Collections.singletonList(error)));
    }
}
