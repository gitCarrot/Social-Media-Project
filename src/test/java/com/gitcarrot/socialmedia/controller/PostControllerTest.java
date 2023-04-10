package com.gitcarrot.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitcarrot.socialmedia.controller.request.PostCreateRequest;
import com.gitcarrot.socialmedia.controller.request.UserJoinRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser
    void writing_post() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body)))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void writing_post_without_signing_in() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body)))
        ).andDo(print()).andExpect(status().isUnauthorized());
    }


}
