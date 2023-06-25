package com.example.board.dto.common;

import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer internalErrorCode;
    private String message = "success";
    private Object result;

    public Integer getInternalErrorCode() {
        return internalErrorCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

    public static Message getErrorMessage(CustomException customException){
        ExceptionMessage exceptionMessage = customException.getExceptionMessage();

        return new Builder().message(exceptionMessage.getMessage())
                .internalErrorCode(exceptionMessage.getInternalCode())
                .build();
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder {
        private Integer internalErrorCode;
        private String message = "success";
        private Object result;

        public Builder internalErrorCode(Integer internalErrorCode) {
            this.internalErrorCode = internalErrorCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder result(Object result) {
            this.result = result;
            return this;
        }

        public Message build() {
            Message messageObject = new Message();
            messageObject.internalErrorCode = this.internalErrorCode;
            messageObject.message = this.message;
            messageObject.result = this.result;
            return messageObject;
        }
    }
}
