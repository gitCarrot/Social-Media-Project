package com.gitcarrot.socialmedia.controller.response;

import com.gitcarrot.socialmedia.model.User;
import com.gitcarrot.socialmedia.model.UserRole;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserJoinResponse {

    private Integer id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
              user.getId(),
              user.getUserName(),
              user.getUserRole()
        );
    }
}
