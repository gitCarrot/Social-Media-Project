package com.gitcarrot.socialmedia.controller;

import com.gitcarrot.socialmedia.controller.request.UserJoinRequest;
import com.gitcarrot.socialmedia.controller.request.UserLoginRequest;
import com.gitcarrot.socialmedia.controller.response.Response;
import com.gitcarrot.socialmedia.controller.response.UserJoinResponse;
import com.gitcarrot.socialmedia.controller.response.UserLoginResponse;
import com.gitcarrot.socialmedia.model.User;
import com.gitcarrot.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getUserName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request){
        String token = userService.login(request.getUserName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
