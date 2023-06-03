package com.example.board.service;

import org.springframework.stereotype.Service;

import com.example.board.mybatis.dao.UserDAO;
import com.example.board.mybatis.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void authenticate(String username, String password) {
        UserVO user = userDAO.findByAccountId(username);
        
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }
    
}
