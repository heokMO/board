package com.example.board.config;

import com.example.board.util.LoginCheckResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginCheckResolver loginCheckResolver;

    public WebConfig(LoginCheckResolver loginCheckResolver) {
        this.loginCheckResolver = loginCheckResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginCheckResolver);
    }
}
