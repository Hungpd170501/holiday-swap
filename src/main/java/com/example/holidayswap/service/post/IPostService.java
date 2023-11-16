package com.example.holidayswap.service.post;

import com.example.holidayswap.domain.dto.response.post.PostResponse;

import java.util.List;

public interface IPostService {
    void reactToPost(Long postId, String reaction);

    void createPost(String content);

    List<PostResponse> getAllPosts();
}
