package com.example.board.service;

import com.example.board.exception.CustomException;

public interface UserService {
    void authenticate(String username, String password) throws CustomException;
}
