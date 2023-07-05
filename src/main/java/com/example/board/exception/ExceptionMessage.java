package com.example.board.exception;

public enum ExceptionMessage {
    UsernameFail("사용자의 ID가 잘못되었습니다.", 101),
    PasswordFail("비밀번호가 잘못되었습니다", 102),
    SessionIdNotFound("해당 sessionId를 찾지 못하였습니다.", 103),
    SessionIdInvalid("해당 세션은 유효하지 않습니다.", 104);

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
