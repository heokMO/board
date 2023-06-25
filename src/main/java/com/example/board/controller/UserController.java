package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Message> processLogin(@RequestParam("username") String username,
                                                @RequestParam("password") String password) {

    }
}
