package com.example.board.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {

    @Bean
    public BCrypt.Hasher bCrypt(){
        return BCrypt.withDefaults();
    }
}
