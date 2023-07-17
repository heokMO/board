package com.example.board.service;

import com.example.board.exception.CustomException;

public interface UserService {
    String authenticate(String username, String password) throws CustomException;

    void checkUsername(String username) throws CustomException;
}
