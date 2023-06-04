package com.example.board.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.board.exception.login.AuthenticationException;
import com.example.board.exception.login.PasswordAuthenticationException;
import com.example.board.exception.login.UsernameAuthenticationException;
import com.example.board.mybatis.dao.UserDAO;
import com.example.board.mybatis.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void authenticate(String username, String password) throws AuthenticationException {
        UserVO user = userDAO.findByAccountId(username);
        if(user == null){
            throw new UsernameAuthenticationException();
        }

        if(BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
            return;
        }

        throw new PasswordAuthenticationException();
    }
    
}
