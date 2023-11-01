package com.example.board.util;

import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class TokenUtil {
    private static final long EXPIRATION_PERIOD = 60 * 60 * 24;
    private final Logger log = LoggerFactory.getLogger(TokenUtil.class);
    private final ObjectMapper objectMapper;


    private final Set<String> loginUser = new HashSet<>();

    public TokenUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String create(String username){
        try {
            LocalDateTime now = LocalDateTime.now();
            Token token = new Token(username, now.plusSeconds(EXPIRATION_PERIOD));
            loginUser.add(username);
            return objectMapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            log.error("", e);
            throw new CustomException(ExceptionMessage.TokenCreateFail);
        }
    }
    public void check(String tokenStr){
        try {
            Token token = objectMapper.readValue(tokenStr, Token.class);
            if (token.getExpiration().isBefore(LocalDateTime.now())) {
                loginUser.remove(token.getUsername());
                throw new CustomException(ExceptionMessage.ExpiredToken);
            }
            if(!loginUser.contains(token.getUsername())){
                throw new CustomException(ExceptionMessage.InvalidToken);
            }
        } catch (JsonProcessingException e) {
            throw new CustomException(ExceptionMessage.TokenParseFail);
        }
    }

    public void delete(String tokenStr) {
        try {
            Token token = objectMapper.readValue(tokenStr, Token.class);
            loginUser.remove(token.getUsername());
        } catch (JsonProcessingException e) {
            throw new CustomException(ExceptionMessage.TokenParseFail);
        }
    }
}
class Token{
    private String username;
    private LocalDateTime expiration;

    public Token() {

    }

    public Token(String username, LocalDateTime expiration) {
        this.username = username;
        this.expiration = expiration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "Token{" +
                "username='" + username + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
