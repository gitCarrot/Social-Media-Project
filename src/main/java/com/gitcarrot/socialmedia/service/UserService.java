package com.gitcarrot.socialmedia.service;


import com.gitcarrot.socialmedia.exception.ErrorCode;
import com.gitcarrot.socialmedia.exception.SocialMediaApplicationException;
import com.gitcarrot.socialmedia.model.User;
import com.gitcarrot.socialmedia.model.entity.UserEntity;
import com.gitcarrot.socialmedia.repository.UserEntityRepository;
import com.gitcarrot.socialmedia.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms")
    private Long expiredTimeMs;

    @Transactional
    public User join(String userName, String password) {
        //Already has Username?
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SocialMediaApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        //register UserName
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));

        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        // is already registered
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SocialMediaApplicationException(ErrorCode.USER_NOT_FOUND,  String.format("%s not founded", userName)));


        //check password
        if(encoder.matches(password, userEntity.getPassword())){
//        if(!userEntity.getPassword().equals(password)){
            throw new SocialMediaApplicationException(ErrorCode.INVALID_PASSWORD, "");
        }

        //token
        String token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs );

        return token;
    }

}
