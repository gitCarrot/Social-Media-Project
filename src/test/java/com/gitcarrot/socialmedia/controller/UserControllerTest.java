package com.gitcarrot.socialmedia.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitcarrot.socialmedia.controller.request.UserJoinRequest;
import com.gitcarrot.socialmedia.controller.request.UserLoginRequest;
import com.gitcarrot.socialmedia.exception.ErrorCode;
import com.gitcarrot.socialmedia.exception.SocialMediaApplicationException;
import com.gitcarrot.socialmedia.model.User;
import com.gitcarrot.socialmedia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void register() throws Exception {
        String userName = "userNmae";
        String password = "1234";

        when(userService.join(userName, password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void register_already_registered_by_userName() throws Exception {
        String userName = "userNmae";
        String password = "1234";

        when(userService.join(userName, password)).thenThrow(new SocialMediaApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
        ).andDo(print()).andExpect(status().isConflict());
    }

    @Test
    public void login() throws Exception {
        String userName = "userNmae";
        String password = "1234";

        when(userService.login(userName, password)).thenReturn("test_token");

        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
        ).andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void login_error_by_no_userName() throws Exception {
        String userName = "userNmae";
        String password = "1234";

        when(userService.login(userName , password)).thenThrow(new SocialMediaApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
        ).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void login_error_by_wrong_password() throws Exception {
        String userName = "userNmae";
        String password = "1234";

        when(userService.login(userName, password)).thenThrow(new SocialMediaApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
        ).andDo(print()).andExpect(status().isUnauthorized());
    }


}
