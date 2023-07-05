package com.example.board.config;

import com.example.board.service.UserService;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class SessionConfig implements HttpSessionListener {
    private final UserService userService;

    public SessionConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionID = se.getSession().getId();
        userService.deleteSession(sessionID);

        HttpSessionListener.super.sessionDestroyed(se);
    }
}
