package com.example.board.util;

import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class LoginChecker {
    private final static Logger log = LoggerFactory.getLogger(LoginChecker.class);
    private final UserService userService;

    public LoginChecker(UserService userService) {
        this.userService = userService;
    }

    public void check(HttpServletRequest httpServletRequest)  throws CustomException{
        HttpSession session = httpServletRequest.getSession(false);
        if(session == null){
            log.error("Session id not found.");
            throw new CustomException(ExceptionMessage.SessionIdNotFound);
        }
        String sessionId = session.getId();

        if(userService.isLogin(sessionId)){
            return;
        }
        log.error("Session id is not valid.");
        throw new CustomException(ExceptionMessage.SessionIdInvalid);
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
