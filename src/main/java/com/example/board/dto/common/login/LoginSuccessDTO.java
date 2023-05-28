package com.example.board.dto.common.login;

import com.example.board.dto.common.ResultDTO;

public class LoginSuccessDTO implements ResultDTO{
    private static final long serialVersionUID = 1L;

    private final String jwt;

    public LoginSuccessDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }    
}
