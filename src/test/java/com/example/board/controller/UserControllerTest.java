package com.example.board.controller;

import com.example.board.dto.request.LoginRequest;
import com.example.board.service.UserService;
import com.example.board.util.CookieEncryptionUtil;
import com.example.board.util.LoginChecker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LoginChecker loginChecker;

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

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("user"));
    }

    @Test
    void checkLogin() throws Exception{
        String username = "test123";
        String cookie = CookieEncryptionUtil.encrypt(username);

        mockMvc.perform(post("/login-check").cookie(new Cookie("user", cookie)))
                .andExpect(status().isOk());
    }

    @Test
    void processLogout() throws Exception{
        String username = "test123";
        String cookie = CookieEncryptionUtil.encrypt(username);

        mockMvc.perform(post("/logout").cookie(new Cookie("user", cookie)))
                .andExpect(status().isOk())
                .andExpect(cookie().maxAge("user", 0));
    }
}