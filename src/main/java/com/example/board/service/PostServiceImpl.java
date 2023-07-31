package com.example.board.service;

import com.example.board.dao.PostDAO;
import com.example.board.dto.post.PostReadDTO;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.vo.PostVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{
    private final static Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostDAO postDAO;
    public PostServiceImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public boolean isLoginRequired(String id) {
        Boolean isLoginRequired = postDAO.isLoginRequired(id);
        if(isLoginRequired == null){
            log.error("Not found post. ID: {}", id);
            throw new CustomException(ExceptionMessage.PostNotFoundException);
        }
        return isLoginRequired;
    }

    @Override
    public PostReadDTO findById(String id) {
        PostVO vo = postDAO.findById(id);
        if (vo == null){
            log.error("Not found post. ID: {}", id);
            throw new CustomException(ExceptionMessage.PostNotFoundException);
        }

        PostReadDTO dto = PostReadDTO.builder()
                .id(vo.getId())
                .title(vo.getTitle())
                .writer(vo.getWriterNickname())
                .updateTime(vo.getUpdateTime())
                .contents(vo.getContent())
                .views(vo.getViews() + 1)
                .build();
        increaseViews(vo);
        return dto;
    }

    private void increaseViews(PostVO vo) {
        postDAO.increaseViews(vo.getId());
    }


}
