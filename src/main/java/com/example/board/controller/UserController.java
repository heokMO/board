package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.dto.request.LoginRequest;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import com.example.board.util.NullChecker;
import com.example.board.vo.UserSessionVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Message> processLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        String username;
        String password;
        Long userID;
        try{
            username = NullChecker.check(loginRequest.getUsername(), new CustomException(ExceptionMessage.UsernameFail));
            password = NullChecker.check(loginRequest.getPassword(), new CustomException(ExceptionMessage.PasswordFail));
            userID = userService.authenticate(username, password);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(e));
        }
        HttpSession oldSession = httpServletRequest.getSession(false);
        if(oldSession != null){
            oldSession.invalidate();
        }
        HttpSession newSession = httpServletRequest.getSession(true);
        String sessionId = newSession.getId();
        LocalDateTime expirationTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(newSession.getLastAccessedTime() + newSession.getMaxInactiveInterval() * 1000L),
                ZoneId.systemDefault()
        );
        UserSessionVO userSessionVO = UserSessionVO.builder()
                .userId(userID)
                .sessionId(sessionId)
                .expirationTime(expirationTime)
                .build();
        userService.createSession(userSessionVO);

        return ResponseEntity.ok(Message.builder().build());
    }

    @PostMapping("/login-check")
    public ResponseEntity<Message> loginCheck(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        if(session == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(ExceptionMessage.SessionIdNotFound));
        }
        String sessionId = session.getId();

        if(userService.isLogin(sessionId)){
            return ResponseEntity.ok(Message.builder().build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(ExceptionMessage.SessionIdInvalid));
    }

    @PostMapping("/logout")
    public ResponseEntity<Message> processLogout(HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null){
            httpSession.invalidate();
        }
        return ResponseEntity.ok(Message.builder().build());
    }
}
