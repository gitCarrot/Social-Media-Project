package com.gitcarrot.socialmedia.service;

import com.gitcarrot.socialmedia.exception.ErrorCode;
import com.gitcarrot.socialmedia.exception.SocialMediaApplicationException;
import com.gitcarrot.socialmedia.fixture.UserEntityFixture;
import com.gitcarrot.socialmedia.model.entity.UserEntity;
import com.gitcarrot.socialmedia.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;


    @Test
    void validResgister() {
        String userName = "userName";
        String password = "1234";

        UserEntity fixture = UserEntityFixture.get(userName, password);
        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(fixture);

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @Test
    void Resgister_Fail_Already_User_Exist() {
        String userName = "userName";
        String password = "1234";
        UserEntity fixture = UserEntityFixture.get(userName, password);
        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        SocialMediaApplicationException e = Assertions.assertThrows(SocialMediaApplicationException.class , () -> userService.join(userName, password));
        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, e.getErrorCode());
    }

    @Test
    void login_success() {
        String userName = "userName";
        String password = "1234";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @Test
    void login_fail_by_no_userName() {
        String userName = "userName";
        String password = "1234";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        SocialMediaApplicationException e =Assertions.assertThrows(SocialMediaApplicationException.class , () -> userService.login(userName, password));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void login_fail_by_wrong_password() {
        String userName = "userName";
        String password = "1234";
        String wrongPassword = "wrongPassword";
        UserEntity fixture = UserEntityFixture.get(userName, password);
        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        SocialMediaApplicationException e = Assertions.assertThrows(SocialMediaApplicationException.class , () -> userService.login(userName, wrongPassword));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
    }
}
