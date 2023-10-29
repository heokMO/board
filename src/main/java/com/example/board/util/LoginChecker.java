package com.example.board.util;

import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class LoginChecker {
    private final static Logger log = LoggerFactory.getLogger(LoginChecker.class);
    private final TokenUtil tokenUtil;

    public LoginChecker(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    public void check(HttpServletRequest httpServletRequest)  throws CustomException{
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null){
            log.error("cookies is null");
            throw new CustomException(ExceptionMessage.CookieNotFoundError);
        }
        try{
            Optional<Cookie> cookie = Arrays.stream(cookies).filter(e-> e.getName().equals("user")).findFirst();
            String encryptedToken = cookie.orElseThrow().getValue();
            String token = CookieEncryptionUtil.decrypt(encryptedToken);
            tokenUtil.check(token);
        } catch (NoSuchElementException e){
            String cookieKeyList = Arrays.stream(cookies)
                    .map(Cookie::getName)
                    .reduce("", (a, b)-> a + "," +b);
            log.error("Username Cookie is not found. cookie key list: {}", cookieKeyList);
            throw new CustomException(ExceptionMessage.UsernameCookieNotFoundError);
        }
    }

    public boolean isLogin(HttpServletRequest httpServletRequest) {
        try{
            check(httpServletRequest);
            return true;
        } catch (CustomException ignored){
            return false;
        }
    }
}
