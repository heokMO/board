package com.example.board.service;

import com.example.board.dao.PostDAO;
import com.example.board.dto.post.PostReadDTO;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.vo.PostVO;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{
    private final PostDAO postDAO;
    public PostServiceImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public boolean isLoginRequired(String id) {
        return postDAO.isLoginRequired(id);
    }

    @Override
    public PostReadDTO findById(String id) throws CustomException {
        PostVO vo = postDAO.findById(id);
        if (vo == null){
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
