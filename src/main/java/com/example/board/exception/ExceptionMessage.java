package com.example.board.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessage {
    UsernameFail("사용자의 ID가 잘못되었습니다.", HttpStatus.UNAUTHORIZED, 101),
    PasswordFail("비밀번호가 잘못되었습니다", HttpStatus.UNAUTHORIZED, 102),
    UsernameEncryptFail("username을 암호화 실패하였습니다.", HttpStatus.UNAUTHORIZED, 103),
    CookieNotFoundError("Cookie를 찾지 못하였습니다.", HttpStatus.BAD_REQUEST, 104),
    UsernameCookieNotFoundError("username에 관한 cookie를 찾지 못하였습니다.", HttpStatus.UNAUTHORIZED, 105),
    CookieDecryptError("Cookie를 복호화하는데에 실패하였습니다.", HttpStatus.UNAUTHORIZED, 106),
    InvalidCookieUsername("Cookie의 username이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED, 107),
    LoginRequiredRequestFail("로그인이 필요한 요청이나 로그인 중이 아닙니다", HttpStatus.UNAUTHORIZED, 108),

    PostNotFoundException("해당 게시물은 존재하지 않습니다.", HttpStatus.NOT_FOUND, 201),
    TokenCreateFail("Token을 생성하는데 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR, 109),
    ExpiredToken("Token이 만료되었습니다.", HttpStatus.UNAUTHORIZED, 110),
    TokenParseFail("Token을 파싱하는데 실패하였습니다.", HttpStatus.BAD_REQUEST, 111),
    InvalidToken("유효하지 않은 Token입니다.", HttpStatus.BAD_REQUEST, 112),
    TokenNotFound("해당 토큰을 찾지 못하였습니다.", HttpStatus.UNAUTHORIZED, 113);


    private final String message;
    private final HttpStatus httpStatus;
    private final int internalCode;

    ExceptionMessage(String message, HttpStatus httpStatus, int internalCode) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.internalCode = internalCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getInternalCode() {
        return internalCode;
    }
}
