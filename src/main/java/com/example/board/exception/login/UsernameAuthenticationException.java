package com.example.board.exception.login;



public class UsernameAuthenticationException extends AuthenticationException {
    public UsernameAuthenticationException() {
        super("Username is invalid");
    }
}
