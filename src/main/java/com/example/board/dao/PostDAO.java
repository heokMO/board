package com.example.board.dao;

import com.example.board.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDAO {

    boolean isLoginRequired(String id);

    PostVO findById(String id);

    void increaseViews(Long id);
}
