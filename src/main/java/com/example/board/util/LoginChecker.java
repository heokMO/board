package com.example.board.util;

import com.example.board.dto.common.Message;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class LoginChecker {
    private final static Logger log = LoggerFactory.getLogger(LoginChecker.class);
    private final UserService userService;

    public LoginChecker(UserService userService) {
        this.userService = userService;
    }

    public void check(HttpServletRequest httpServletRequest)  throws CustomException{
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
            log.info("Username Cookie is not found. cookie key list: {}", cookieKeyList);
            throw new CustomException(ExceptionMessage.UsernameCookieNotFoundError);
        }
    }
}
