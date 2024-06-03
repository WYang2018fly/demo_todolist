package com.todo.exception;

import lombok.Data;

@Data
public class AuthException extends RuntimeException {
    private String reason;
    private String errMessage;

    public AuthException(String[] authException) {
        super(authException[1]);
        this.reason = authException[0];
        this.errMessage = authException[1];
    }
}
