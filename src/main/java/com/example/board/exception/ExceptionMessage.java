package com.example.board.exception;

public enum ExceptionMessage {
    UsernameFail("사용자의 ID가 잘못되었습니다.", 101),
    PasswordFail("비밀번호가 잘못되었습니다", 102);

    private final String message;
    private final int internalCode;

    ExceptionMessage(String message, int internalCode) {
        this.message = message;
        this.internalCode = internalCode;
    }

    public String getMessage() {
        return message;
    }

    public int getInternalCode() {
        return internalCode;
    }
}
