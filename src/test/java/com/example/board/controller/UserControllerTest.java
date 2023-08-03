package com.example.board.controller;

import com.example.board.dto.request.LoginRequest;
import com.example.board.service.UserService;
import com.example.board.util.AuthenticationHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuthenticationHelper authenticationHelper;

    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void processLogin() throws Exception {
        String username = "test123";
        String password = "test";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());
        verify(userService).authenticate(username, password);
    }

    @Test
    void loginCheck() throws Exception {
        mockMvc.perform(post("/login-check")).andExpect(status().isOk());

        verify(authenticationHelper, times(1)).check(any());
    }

    @Test
    void processLogout() throws Exception {
        mockMvc.perform(post("/logout")).andExpect(status().isOk());

        verify(authenticationHelper, times(1)).logout(any(), any());
    }
}