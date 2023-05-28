package com.example.board.dto.common;

public class FailDTO extends MessageDTO{
    private static final long serialVersionUID = 1L;
    private final Integer code;
    private final String message;

    public FailDTO(Integer status, Integer code, String message) {
        super(status);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder {
        private Integer status;
        private Integer code;
        private String message;

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public FailDTO build() {
            return new FailDTO(status, code, message);
        }
    }
    
    
}
