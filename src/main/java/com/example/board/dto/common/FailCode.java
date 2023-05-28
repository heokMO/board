package com.example.board.dto.common;

public enum FailCode {
    USERNAME_FAIL(1, "Username is invalid."),
    PASSWORD_FAIL(2, "Password is invalid.");

    private final Integer code;
    private final String message;
    private FailCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    
    
}
