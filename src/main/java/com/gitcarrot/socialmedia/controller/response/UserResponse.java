package com.gitcarrot.socialmedia.controller.response;

import com.gitcarrot.socialmedia.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class UserResponse {
    private Integer id;
    private String userName;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName()
        );
    }

}