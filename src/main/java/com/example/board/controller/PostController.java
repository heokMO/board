package com.example.board.controller;

import com.example.board.aspect.LoginRequired;
import com.example.board.dto.common.Message;
import com.example.board.dto.post.PostReadDTO;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.PostService;
import com.example.board.util.LoginChecker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> read(@PathVariable String id, HttpServletRequest httpServletRequest) throws CustomException {
        PostReadDTO post;
        if(postService.isLoginRequired(id)){
            post = getLoginRequiredPost(id);
        } else {
            post = getPost(id);
        }
        return ResponseEntity.ok(Message.builder().result(post).build());
    }


    public PostReadDTO getPost(String id) throws CustomException {
        return postService.findById(id);
    }

    @LoginRequired
    public PostReadDTO getLoginRequiredPost(String id) throws CustomException {
        return getPost(id);
    }
}
