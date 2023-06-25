package com.example.board.service;

import com.example.board.exception.CustomException;
import com.example.board.vo.UserSessionVO;

public interface UserService {
    Long authenticate(String username, String password) throws CustomException;
    void createSession(UserSessionVO userSessionVO);
    void deleteSession(String sessionID);
}
