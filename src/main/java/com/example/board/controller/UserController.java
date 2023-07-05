package com.example.board.controller;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

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
            log.error(">>>", e);
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

    @PostMapping("/login-check")
    public ResponseEntity<Message> checkLogin(HttpServletRequest httpServletRequest){
        //find cookie value(encryptedUsername)
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(ExceptionMessage.CookieNotFoundError));
        }
        Optional<Cookie> cookie = Arrays.stream(cookies).filter(e-> e.getName().equals("user")).findFirst();
        String encryptedUsername;
        try{
             encryptedUsername = cookie.orElseThrow().getValue();
        } catch (NoSuchElementException e){
            String cookieKeyList = Arrays.stream(cookies)
                    .map(Cookie::getName)
                    .reduce("", (a, b)-> a + "," +b);
            log.info("Username Cookie is not found. cookie key list: {}", cookieKeyList);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(ExceptionMessage.UsernameCookieNotFoundError));
        }
        // encrypted username decrypt
        String username;
        try{
            username = CookieEncryptionUtil.decrypt(encryptedUsername);
        } catch (Exception e){
           log.error("decrypt failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Message.getErrorMessage(ExceptionMessage.CookieDecryptError));
        }
        // Username validation check
        if(!userService.exists(username)){
            log.error("Invalid username. username : {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.builder().build());
        }

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
