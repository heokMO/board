package com.example.board.controller;

import com.example.board.aspect.LoginRequired;
import com.example.board.dto.common.Message;
import com.example.board.dto.request.LoginRequest;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.UserService;
import com.example.board.util.AuthenticationHelper;
import com.example.board.util.NullChecker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    public UserController(UserService userService, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping("/login")
    public ResponseEntity<Message> processLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String username = NullChecker.check(loginRequest.getUsername(), new CustomException(ExceptionMessage.UsernameFail));
        String password = NullChecker.check(loginRequest.getPassword(), new CustomException(ExceptionMessage.PasswordFail));
        userService.authenticate(username, password);

        authenticationHelper.processLogin(username, password, httpServletRequest, httpServletResponse);

        return ResponseEntity.ok(Message.builder().build());
    }

    @LoginRequired
    @PostMapping("/login-check")
    public ResponseEntity<Message> loginCheck(){
        return ResponseEntity.ok(Message.builder().build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Message> processLogout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        authenticationHelper.logout(httpServletRequest, httpServletResponse);
        return ResponseEntity.ok(Message.builder().build());
    }
}
