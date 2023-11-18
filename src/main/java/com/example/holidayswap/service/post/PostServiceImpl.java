package com.example.holidayswap.service.post;

import com.example.holidayswap.domain.dto.response.post.PostResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.post.Post;
import com.example.holidayswap.domain.entity.post.UserReactionPost;
import com.example.holidayswap.domain.entity.post.UserReactionPostId;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.post.PostRepository;
import com.example.holidayswap.repository.post.UserReactPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements IPostService{
    private final PostRepository postRepository;
    private final UserReactPostRepository userReactPostRepository;
    private final UserRepository userRepository;

    @Override
    public void reactToPost(Long postId, String reaction) {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
//        User user = userRepository.findById(userId).orElse(null);
        var userReactPost = userReactPostRepository.findByPostIdAndUser(postId, user);
        if(userReactPost == null){
            userReactPost = new UserReactionPost();
            UserReactionPostId userReactionPostId = new UserReactionPostId();
            userReactionPostId.setPostId(postId);
            userReactionPostId.setUserId(user.getUserId());
            userReactPost.setUserReactionPostId(userReactionPostId);
            userReactPost.setPost(postRepository.findById(postId).orElse(null));
            userReactPost.setUser(user);
        }
        if(reaction.equals("like")) {
            if(userReactPost.isLike()){
                userReactPost.setLike(false);
            }
            else{
                userReactPost.setLike(true);
            }
            userReactPost.setDislike(false);
        }
        else if(reaction.equals("dislike")){
            if(userReactPost.isDislike()){
                userReactPost.setDislike(false);
            }
            else{
                userReactPost.setDislike(true);
            }
            userReactPost.setLike(false);
        }
        userReactPostRepository.save(userReactPost);
    }

    @Override
    public void createPost(String content) {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        Post post = new Post();
        post.setContent(content);
        post.setUser(user);
        post.setUserId(user.getUserId());
        post.setDatePosted(new java.util.Date());
        postRepository.save(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        var post = postRepository.findAll();
        List<PostResponse> postResponse = new ArrayList<>();
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;

        if(post != null){
            for (Post p: post) {
                var userPostId = new UserReactionPostId();
                userPostId.setPostId(p.getId());
                userPostId.setUserId(user.getUserId());
                var userLike = userReactPostRepository.findAllByPostAndLike(p);
                var userDislike = userReactPostRepository.findAllByPostAndDislike(p);
                var userReactPost = userReactPostRepository.findByUserReactionPostId(userPostId);


                PostResponse postResponse1 = new PostResponse();
                postResponse1.setContent(p.getContent());
                postResponse1.setDatePosted(p.getDatePosted());
                postResponse1.setId(p.getId());
                postResponse1.setUserName(p.getUser().getUsername());
                postResponse1.setAvatar(p.getUser().getAvatar());
                postResponse1.setLikes(userLike.size());
                postResponse1.setDislikes(userDislike.size());
                postResponse1.setLiked(userReactPost == null ? false : userReactPost.isLike());
                postResponse1.setDisliked(userReactPost == null ? false : userReactPost.isDislike());
                postResponse.add(postResponse1);

            }
        }
        return postResponse;
    }


}
