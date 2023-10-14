package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Message> handleCustomException(CustomException e){
        ExceptionMessage exceptionMessage = e.getExceptionMessage();
        return ResponseEntity.status(exceptionMessage.getHttpStatus()).body(Message.getErrorMessage(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message>handleUnCatchException(Exception e){
        logger.error("UnCatchException.", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Message.builder().message(e.getMessage()).build());
    }
}
