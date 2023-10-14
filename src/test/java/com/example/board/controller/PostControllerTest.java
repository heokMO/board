package com.example.board.controller;

import com.example.board.service.PostService;
import com.example.board.util.LoginCheckResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoginCheckResolver loginCheckResolver;

    @MockBean
    PostService postService;

    @Test
    void loginRequiredRead() throws Exception {
        String postId = "1";
        when(loginCheckResolver.supportsParameter(any())).thenReturn(true);
        when(loginCheckResolver.resolveArgument(any(), any(), any(), any())).thenReturn(true);
        when(postService.hasMemberAccess(postId)).thenReturn(true);

        mockMvc.perform(get("/post/1")).andExpect(status().isOk());
        verify(loginCheckResolver, times(1)).resolveArgument(any(), any(), any(), any());
    }
}