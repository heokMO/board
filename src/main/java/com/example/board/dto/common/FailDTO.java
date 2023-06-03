package com.example.board.dto.common;

public class FailDTO extends MessageDTO{
    private static final long serialVersionUID = 1L;
    private final Integer internalCode;
    private final String message;

    public FailDTO(Integer status, Integer code, String message) {
        super(status);
        this.internalCode = code;
        this.message = message;
    }

    public FailDTO(Integer status, FailCode failCode){
        super(status);
        this.internalCode = failCode.getCode();
        this.message = failCode.getMessage();
    }

    public Integer getInternalCode() {
        return internalCode;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Integer status;
        private Integer internalCode;
        private String message;

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder internalCode(Integer code) {
            this.internalCode = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public FailDTO build() {
            return new FailDTO(status, internalCode, message);
        }
    }
    
    
}
