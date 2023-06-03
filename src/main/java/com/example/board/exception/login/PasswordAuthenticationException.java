package com.example.board.exception.login;

public class PasswordAuthenticationException extends AuthenticationException{
    public PasswordAuthenticationException() {
        super("Password is invalid");
    }
}
