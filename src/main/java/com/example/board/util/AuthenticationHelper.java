package com.example.board.util;

import com.example.board.exception.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationHelper {
    void check(HttpServletRequest httpServletRequest) throws CustomException;
    boolean isLogin(HttpServletRequest httpServletRequest);
    void processLogin(String username, String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
