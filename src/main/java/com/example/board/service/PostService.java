package com.example.board.service;

import com.example.board.dto.post.PostReadDTO;
import com.example.board.exception.CustomException;

public interface PostService {
    boolean isLoginRequired(String id);

    PostReadDTO findById(String id);
}
