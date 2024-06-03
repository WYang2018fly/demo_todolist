package com.todo.controller;

import com.todo.dto.ErrorResponseDTO;
import com.todo.exception.AuthException;
import com.todo.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponseDTO> handleBizException(BizException e, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getReason(), e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthException(AuthException e, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getReason(), e.getErrMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception e, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Internal Server Error", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}