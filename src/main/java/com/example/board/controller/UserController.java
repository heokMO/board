package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.dto.request.LoginRequest;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import com.example.board.util.NullChecker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private final static int LOGIN_COOKIE_DEFAULT_MAX_AGE = 24 * 60 * 60;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Message> processLogin(@RequestBody LoginRequest loginRequest,
                                                HttpServletResponse response) {
        String username;
        String password;
        try {
            username = NullChecker.check(loginRequest.getUsername(), new CustomException(ExceptionMessage.UsernameFail));
            password = NullChecker.check(loginRequest.getPassword(), new CustomException(ExceptionMessage.PasswordFail));
            userService.authenticate(username, password);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(e));
        }
        Cookie cookie = new Cookie("user", username);
        cookie.setMaxAge(LOGIN_COOKIE_DEFAULT_MAX_AGE);
        response.addCookie(cookie);

        return ResponseEntity.ok(Message.builder().build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Message> processLogout(HttpServletResponse response){
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        return ResponseEntity.ok(Message.builder().build());
    }
}
