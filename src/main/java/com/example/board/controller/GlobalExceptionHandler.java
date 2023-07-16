package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Message> handleCustomException(CustomException e){
        ExceptionMessage exceptionMessage = e.getExceptionMessage();
        return ResponseEntity.status(exceptionMessage.getHttpStatus()).body(Message.getErrorMessage(e));
    }
}
