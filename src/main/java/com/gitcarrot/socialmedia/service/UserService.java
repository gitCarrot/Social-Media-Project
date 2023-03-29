package com.gitcarrot.socialmedia.service;


import com.gitcarrot.socialmedia.exception.SocialMediaApplicationException;
import com.gitcarrot.socialmedia.model.User;
import com.gitcarrot.socialmedia.model.entity.UserEntity;
import com.gitcarrot.socialmedia.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;


    public User join(String userName, String password) {
        //Already has Username?
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SocialMediaApplicationException();
        });

        //register UserName
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, password));

        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        // is already registered
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SocialMediaApplicationException());


        //check password
        if(!userEntity.getPassword().equals(password)){
            throw new SocialMediaApplicationException();
        }

        //token

        return "";
    }

}
