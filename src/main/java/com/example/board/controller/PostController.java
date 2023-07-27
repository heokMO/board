package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.dto.post.PostReadDTO;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.PostService;
import com.example.board.util.IsLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> read(@PathVariable String id, @IsLogin boolean isLogin) throws CustomException {
        PostReadDTO post;
        if (postService.isLoginRequired(id) && !isLogin) {
            log.error("The board is required login. Post ID: {}", id);
            throw new CustomException(ExceptionMessage.LoginRequiredRequestFail);
        }
        post = postService.findById(id);
        return ResponseEntity.ok(Message.builder().result(post).build());
    }
}
