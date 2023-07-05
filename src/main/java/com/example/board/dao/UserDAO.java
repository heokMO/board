package com.example.board.dao;

import com.example.board.vo.UserSessionVO;
import com.example.board.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    UserVO findByAccountId(String username);
    void createSession(UserSessionVO userSessionVO);
    void deleteSession(String sessionID);

    boolean existSessionId(String sessionId);
}
