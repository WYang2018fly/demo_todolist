package com.todo.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException {
    private String reason;
    private String errorMessage;

    public BizException(String[] bizException) {
        super(bizException[1]);
        this.reason = bizException[0];
        this.errorMessage = bizException[1];
    }
}