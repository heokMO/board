package com.example.board.controller;

import com.example.board.dto.common.FailCode;
import com.example.board.dto.common.FailDTO;
import com.example.board.dto.common.MessageDTO;
import com.example.board.dto.common.SuccessDTO;
import com.example.board.exception.login.AuthenticationException;
import com.example.board.exception.login.PasswordAuthenticationException;
import com.example.board.exception.login.UsernameAuthenticationException;
import com.example.board.security.JwtSupporter;
import com.example.board.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final JwtSupporter jwtSupporter;


    public LoginController(UserService userService, JwtSupporter jwtSupporter) {
        this.userService = userService;
        this.jwtSupporter = jwtSupporter;
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDTO> processLogin(@RequestParam("username") String username,
                                                   @RequestParam("password") String password) {
        try{
            userService.authenticate(username, password);
        } catch (UsernameAuthenticationException userE){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new FailDTO(HttpStatus.UNAUTHORIZED.value(), FailCode.USERNAME_FAIL));
        } catch (PasswordAuthenticationException passwordE){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new FailDTO(HttpStatus.UNAUTHORIZED.value(), FailCode.PASSWORD_FAIL));
        } catch (AuthenticationException e) {
            logger.debug(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtSupporter.generateToken(username));
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(SuccessDTO.builder().build());
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageDTO> processLogout(@RequestHeader("Authorization") String token) {
        String extractedToken = extractBearerToken(token);
        if (jwtSupporter.validateToken(extractedToken)) {
            jwtSupporter.invalidJWT(extractedToken);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.remove("Authorization");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(SuccessDTO.builder().build());
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
