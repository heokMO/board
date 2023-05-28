package com.example.board.dto.user;

import com.example.board.dto.common.ResultDTO;

public class UserDTO implements ResultDTO{
    private static final long serialVersionUID = 1L;
    
    private final String nickname;
    private final int role;
    public UserDTO(String nickname, int role) {
        this.nickname = nickname;
        this.role = role;
    }
    public String getNickname() {
        return nickname;
    }
    public int getRole() {
        return role;
    }

    
    
}
