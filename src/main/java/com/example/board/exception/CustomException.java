package com.example.board.exception;

public class CustomException extends RuntimeException{
    private final ExceptionMessage exceptionMessage;

    public CustomException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }
}
