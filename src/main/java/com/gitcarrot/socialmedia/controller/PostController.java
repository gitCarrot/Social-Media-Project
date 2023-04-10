package com.gitcarrot.socialmedia.controller;


import com.gitcarrot.socialmedia.controller.request.PostCreateRequest;
import com.gitcarrot.socialmedia.controller.response.Response;
import com.gitcarrot.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor   //for using postService
public class PostController {

    private final PostService postService;


    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication){


        postService.create(request.getTitle(), request.getBody(), authentication.getName());

        return Response.success(null);
    }



}
