package com.example.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.common.MessageDTO;
import com.example.board.dto.common.ResultDTO;
import com.example.board.dto.common.SuccessDTO;
import com.example.board.dto.common.login.LoginSuccessDTO;
import com.example.board.dto.common.login.LogoutSuccessDTO;
import com.example.board.security.JwtUtils;
import com.example.board.service.UserService;

@RestController
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public MessageDTO processLogin(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession httpSession) {

        userService.authenticate(username, password);
        ResultDTO result = new LoginSuccessDTO(JwtUtils.generateToken(username));
        return SuccessDTO.builder()
                .resultDTO(result)
                .build();
    }

    @PostMapping("/logout")
    public MessageDTO processLogout(@RequestHeader("Authorization") String token) {
        if (JwtUtils.validateToken(token)) {
            JwtUtils.invalidJWT(token);
        }
        ResultDTO result = new LogoutSuccessDTO();
        return SuccessDTO.builder()
                .resultDTO(result)
                .build();
    }

}
