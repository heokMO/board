package com.example.board.aspect;

import com.example.board.exception.CustomException;
import com.example.board.util.LoginChecker;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoginAspect {
    private final LoginChecker loginChecker;

    public LoginAspect(LoginChecker loginChecker) {
        this.loginChecker = loginChecker;
    }


    @Before("@annotation(LoginRequired)")
    public void checkLoginStatus() {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        loginChecker.check(httpServletRequest);
    }
}