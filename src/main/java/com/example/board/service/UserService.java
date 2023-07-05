package com.example.board.service;

import com.example.board.exception.CustomException;

public interface UserService {
    String authenticate(String username, String password) throws CustomException;

    boolean exists(String username);
}
