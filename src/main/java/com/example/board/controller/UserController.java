package com.example.board.controller;

import com.example.board.aspect.LoginRequired;
import com.example.board.dto.common.Message;
import com.example.board.dto.request.LoginRequest;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import com.example.board.util.CookieEncryptionUtil;
import com.example.board.util.NullChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private final static int LOGIN_COOKIE_DEFAULT_MAX_AGE = 24 * 60 * 60;
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Message> processLogin(@RequestBody LoginRequest loginRequest,
                                                HttpServletResponse response) {
        String username = null;
        String password = null;
        try {
            username = NullChecker.check(loginRequest.getUsername(), new CustomException(ExceptionMessage.UsernameFail));
            password = NullChecker.check(loginRequest.getPassword(), new CustomException(ExceptionMessage.PasswordFail));
            userService.authenticate(username, password);
        } catch (CustomException e) {
            log.error("User authentication failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(e));
        }
        try{
            Cookie cookie = new Cookie("user", CookieEncryptionUtil.encrypt(username));
            cookie.setMaxAge(LOGIN_COOKIE_DEFAULT_MAX_AGE);
            response.addCookie(cookie);
        } catch (Exception e){
            log.error("cookie encryption Exception." , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Message.getErrorMessage(ExceptionMessage.UsernameEncryptFail));
        }
        return ResponseEntity.ok(Message.builder().build());
    }

    @LoginRequired
    @PostMapping("/login-check")
    public ResponseEntity<Message> checkLogin(){

        return ResponseEntity.ok(Message.builder().build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Message> processLogout(HttpServletResponse response){
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok(Message.builder().build());
    }
}
