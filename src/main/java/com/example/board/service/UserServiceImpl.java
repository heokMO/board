package com.example.board.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.board.dao.UserDAO;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
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
    public boolean exists(String username) {
        return userDAO.existsByAccountId(username);
    }


}
