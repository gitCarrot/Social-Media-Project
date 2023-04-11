package com.gitcarrot.socialmedia.fixture;

import com.gitcarrot.socialmedia.model.entity.PostEntity;
import com.gitcarrot.socialmedia.model.entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String userName, Integer postId){
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUserName(userName);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId)

        return result;
    }

}
