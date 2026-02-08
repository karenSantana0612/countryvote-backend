package com.countryvote.backend.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalAccessError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> badRequest(IllegalArgumentException ex) {
        return Map.of(
                "message", ex.getMessage()
        );
    }

    @ExceptionHandler(DuplicateVoteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleDuplicateVote(DuplicateVoteException ex) {
        return Map.of(
                "error", "DUPLICATE_VOTE",
                "message", ex.getMessage()
        );
    }
}

