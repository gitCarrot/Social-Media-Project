package com.gitcarrot.socialmedia.service;


import com.gitcarrot.socialmedia.exception.ErrorCode;
import com.gitcarrot.socialmedia.exception.SocialMediaApplicationException;
import com.gitcarrot.socialmedia.model.Post;
import com.gitcarrot.socialmedia.model.entity.PostEntity;
import com.gitcarrot.socialmedia.model.entity.UserEntity;
import com.gitcarrot.socialmedia.repository.PostEntityRepository;
import com.gitcarrot.socialmedia.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;



    @Transactional
    public void create(String title, String body, String userName){

        //user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SocialMediaApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        //post save
        PostEntity postEntity = postEntityRepository.save(PostEntity.of(title, body, userEntity));
        //return
    }

    @Transactional
    public Post modify(String title, String body, String userName, Integer postId){

        //user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SocialMediaApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
        //post exist

        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(()
                -> new SocialMediaApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        //post permission
        if (postEntity.getUser() != userEntity) {
            throw new SocialMediaApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with $s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));





    }


}
