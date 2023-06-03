package com.example.board.controller;

import com.example.board.dto.common.*;
import com.example.board.dto.common.login.LogoutSuccessDTO;
import com.example.board.security.JwtSupporter;
import com.example.board.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserService userService;
    private final JwtSupporter jwtSupporter;
    public LoginController(UserService userService, JwtSupporter jwtSupporter) {
        this.userService = userService;
        this.jwtSupporter = jwtSupporter;
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDTO> processLogin(@RequestParam("username") String username,
                                                   @RequestParam("password") String password) {
        userService.authenticate(username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtSupporter.generateToken(username));
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(SuccessDTO.builder().build());
    }

    @PostMapping("/logout")
    public MessageDTO processLogout(@RequestHeader("Authorization") String token) {
        String extractedToken = extractBearerToken(token);
        if (jwtSupporter.validateToken(extractedToken)) {
            jwtSupporter.invalidJWT(extractedToken);
        }
        ResultDTO result = new LogoutSuccessDTO();
        return SuccessDTO.builder()
                .resultDTO(result)
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<MessageDTO> processRefresh(@RequestHeader("Authorization") String token){
        String extractedToken = extractBearerToken(token);
        if(jwtSupporter.validateToken(extractedToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new FailDTO(HttpStatus.UNAUTHORIZED.value(), FailCode.JWT_INVALID));
        }
        String newToken = jwtSupporter.refreshJWT(extractedToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(SuccessDTO.builder().build());
    }


    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
