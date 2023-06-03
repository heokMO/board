package com.example.board.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.mybatis.vo.UserVO;

@Mapper
public interface UserDAO {

    UserVO findByAccountId(String username);
    
}
