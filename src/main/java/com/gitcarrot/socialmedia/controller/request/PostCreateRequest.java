package com.gitcarrot.socialmedia.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class PostCreateRequest {

    private String title;
    private String body;
}
