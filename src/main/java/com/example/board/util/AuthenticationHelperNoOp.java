package com.example.board.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationHelperNoOp implements AuthenticationHelper {

    @Override
    public void check(HttpServletRequest httpServletRequest) {
    }
    @Override
    public boolean isLogin(HttpServletRequest httpServletRequest) {
        return true;
    }
    @Override
    public void processLogin(String username, String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

}
