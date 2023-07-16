package com.example.board.aspect;

import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import com.example.board.util.CookieEncryptionUtil;
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
    private final UserService userService;

    public LoginAspect(HttpServletRequest httpServletRequest, UserService userService) {
        this.httpServletRequest = httpServletRequest;
        this.userService = userService;
    }


    @Before("@annotation(LoginRequired)")
    public void checkLoginStatus() throws CustomException {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null){
            log.info("cookies is null");
            throw new CustomException(ExceptionMessage.CookieNotFoundError);
        }
        try{
            Optional<Cookie> cookie = Arrays.stream(cookies).filter(e-> e.getName().equals("user")).findFirst();
            String encryptedUsername = cookie.orElseThrow().getValue();
            String username = CookieEncryptionUtil.decrypt(encryptedUsername);
            if(!userService.exists(username)){
                log.error("Invalid username. username : {}", username);
                throw new CustomException(ExceptionMessage.InvalidCookieUsername);
            }
        } catch (NoSuchElementException e){
            String cookieKeyList = Arrays.stream(cookies)
                    .map(Cookie::getName)
                    .reduce("", (a, b)-> a + "," +b);
            log.error("Username Cookie is not found. cookie key list: {}", cookieKeyList);
            throw new CustomException(ExceptionMessage.UsernameCookieNotFoundError);
        }
    }
}
