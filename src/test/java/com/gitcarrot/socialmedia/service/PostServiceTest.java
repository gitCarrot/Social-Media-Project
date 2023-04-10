package com.gitcarrot.socialmedia.service;

import com.gitcarrot.socialmedia.exception.ErrorCode;
import com.gitcarrot.socialmedia.exception.SocialMediaApplicationException;
import com.gitcarrot.socialmedia.model.entity.PostEntity;
import com.gitcarrot.socialmedia.model.entity.UserEntity;
import com.gitcarrot.socialmedia.repository.PostEntityRepository;
import com.gitcarrot.socialmedia.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostEntityRepository postEntityRepository;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void post_success(){

        String title = "title";
        String body = "body";
        String userName = "userName";

        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        Assertions.assertDoesNotThrow(() -> postService.create(title, body, userName));

    }

    @Test
    void post_without_requested_user(){

        String title = "title";
        String body = "body";
        String userName = "userName";

        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SocialMediaApplicationException e = Assertions.assertThrows(SocialMediaApplicationException.class, () -> postService.create(title, body, userName));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }
}
