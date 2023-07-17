package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.dto.post.PostReadDTO;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.PostService;
import com.example.board.util.IsLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> read(@PathVariable String id, @IsLogin boolean isLogin) throws CustomException {
        PostReadDTO post;
        if (postService.isLoginRequired(id) && !isLogin) {
            throw new CustomException(ExceptionMessage.LoginRequiredRequestFail);
        }
        post = postService.findById(id);
        return ResponseEntity.ok(Message.builder().result(post).build());
    }
}
