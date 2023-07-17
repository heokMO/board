package com.example.board.aspect;

import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import com.example.board.util.CookieEncryptionUtil;
import com.example.board.util.LoginChecker;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Aspect
@Component
public class LoginAspect {
    private static final Logger log = LoggerFactory.getLogger(LoginAspect.class);
    private final HttpServletRequest httpServletRequest;
    private final LoginChecker loginChecker;

    public LoginAspect(HttpServletRequest httpServletRequest, LoginChecker loginChecker) {
        this.httpServletRequest = httpServletRequest;
        this.loginChecker = loginChecker;
    }


    @Before("@annotation(LoginRequired)")
    public void checkLoginStatus(JoinPoint joinPoint) throws CustomException {
        loginChecker.check(httpServletRequest);
    }
}
