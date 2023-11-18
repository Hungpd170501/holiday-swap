package com.example.holidayswap.controller.post;

import com.example.holidayswap.service.post.IPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final IPostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody String content) {
        postService.createPost(content);
       return ResponseEntity.ok("Post created");
    }
    @PutMapping("/react")
    public ResponseEntity<?> reactToPost(Long postId, String reaction) {
        postService.reactToPost(postId, reaction);
        return ResponseEntity.ok("Post reacted to");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
