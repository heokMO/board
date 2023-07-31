package com.example.board.aspect;

import com.example.board.exception.CustomException;
import com.example.board.util.AuthenticationHelper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoginAspect {
    private final AuthenticationHelper authenticationHelperImpl;

    public LoginAspect(AuthenticationHelper authenticationHelperImpl) {
        this.authenticationHelperImpl = authenticationHelperImpl;
    }


    @Before("@annotation(LoginRequired)")
    public void checkLoginStatus() throws CustomException {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        authenticationHelperImpl.check(httpServletRequest);
    }
}