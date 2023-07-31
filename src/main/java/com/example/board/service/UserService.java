package com.example.board.service;

import com.example.board.exception.CustomException;
import com.example.board.vo.UserSessionVO;

public interface UserService {
    void authenticate(String username, String password) throws CustomException;
}
