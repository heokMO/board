package com.example.board.service;


import com.example.board.exception.login.AuthenticationException;

public interface UserService {

    void authenticate(String username, String password) throws AuthenticationException;
    
}
