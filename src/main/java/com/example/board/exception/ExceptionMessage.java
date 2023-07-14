package com.example.board.exception;

public enum ExceptionMessage {
    UsernameFail("사용자의 ID가 잘못되었습니다.", 101),
    PasswordFail("비밀번호가 잘못되었습니다", 102),
    UsernameEncryptFail("username을 암호화 실패하였습니다.", 103),
    CookieNotFoundError("Cookie를 찾지 못하였습니다.", 104),
    UsernameCookieNotFoundError("username에 관한 cookie를 찾지 못하였습니다.", 105),
    CookieDecryptError("Cookie를 복호화하는데에 실패하였습니다.", 106),
    InvalidCookieUsername("Cookie의 username이 유효하지 않습니다.", 107),
    PostNotFoundException("해당 게시물은 존재하지 않습니다.", 201);

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
