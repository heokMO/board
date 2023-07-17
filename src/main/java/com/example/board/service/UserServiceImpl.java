package com.example.board.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.board.dao.UserDAO;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public String authenticate(String username, String password) throws CustomException {
        UserVO user = userDAO.findByAccountId(username);
        if(user == null){
            throw new CustomException(ExceptionMessage.UsernameFail);
        }

        if(BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
            return username;
        }

        throw new CustomException(ExceptionMessage.PasswordFail);
    }

    @Override
    public void checkUsername(String username) throws CustomException {
        if(userDAO.existsByAccountId(username)){
            return;
        }
        log.error("Invalid username. username : {}", username);
        throw new CustomException(ExceptionMessage.InvalidCookieUsername);
    }


}
