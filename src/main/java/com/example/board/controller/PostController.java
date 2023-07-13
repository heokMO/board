package com.example.board.controller;

import com.example.board.dto.common.Message;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionMessage;
import com.example.board.service.PostService;
import com.example.board.util.LoginChecker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller("/post")
public class PostController {
    private final PostService postService;
    private final LoginChecker loginChecker;

    public PostController(PostService postService, LoginChecker loginChecker) {
        this.postService = postService;
        this.loginChecker = loginChecker;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> read(@PathVariable String id, HttpServletRequest httpServletRequest){
        try{
            if(postService.isLoginRequired(id)){
                loginChecker.check(httpServletRequest);
            }
            return ResponseEntity.ok(Message.builder().result(postService.findById(id)).build());
        }catch (CustomException e){
            ExceptionMessage exceptionMessage = e.getExceptionMessage();
            switch (exceptionMessage){
                case UsernameCookieNotFoundError:
                case CookieNotFoundError:
                case CookieDecryptError:
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message.getErrorMessage(e));
                case PostNotFoundException:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Message.getErrorMessage(e));
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Message.getErrorMessage(e));
            }
        }

    }
}
