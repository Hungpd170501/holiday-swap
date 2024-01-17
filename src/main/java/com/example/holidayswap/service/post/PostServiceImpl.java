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
    public void createPost(String content, String title) {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        Post post = new Post();
        post.setContent(content);
        post.setUser(user);
        post.setTitle(title);
        post.setUserId(user.getUserId());
        post.setDatePosted(new java.util.Date());
        postRepository.save(post);
    }

    @Override
    public List<PostResponse> getAllPosts(Long userId) {
        var post = postRepository.findAll();
        List<PostResponse> postResponse = new ArrayList<>();
        UserReactionPost userReactPost = null;



        if(post != null){
            for (Post p: post) {

                var userPostId = new UserReactionPostId();
                if(userId != null){
                    userPostId.setPostId(p.getId());
                    userPostId.setUserId(userId);
                    userReactPost = userReactPostRepository.findByUserReactionPostId(userPostId);

                }
                var userLike = userReactPostRepository.findAllByPostAndLike(p);
                var userDislike = userReactPostRepository.findAllByPostAndDislike(p);


                PostResponse postResponse1 = new PostResponse();
                postResponse1.setTitle(p.getTitle());
//                postResponse1.setContent(p.getContent());
                postResponse1.setDatePosted(p.getDatePosted());
                postResponse1.setId(p.getId());
                postResponse1.setUserName(p.getUser().getUsername());
                postResponse1.setFullName(p.getUser().getFullName());
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

    @Override
    public PostResponse getPost(Long postId,Long userId ) {
        var post = postRepository.findById(postId).orElse(null);
        var userPostId = new UserReactionPostId();
        UserReactionPost userReactPost = null;
        if(userId != null){
            userPostId.setPostId(postId);
            userPostId.setUserId(userId);
            userReactPost = userReactPostRepository.findByUserReactionPostId(userPostId);

        }
        if(post != null){
            var userLike = userReactPostRepository.findAllByPostAndLike(post);
            var userDislike = userReactPostRepository.findAllByPostAndDislike(post);
            PostResponse postResponse = new PostResponse();
            postResponse.setContent(post.getContent());
            postResponse.setTitle(post.getTitle());
            postResponse.setDatePosted(post.getDatePosted());
            postResponse.setId(post.getId());
            postResponse.setUserName(post.getUser().getUsername());
            postResponse.setAvatar(post.getUser().getAvatar());
            postResponse.setFullName(post.getUser().getFullName());
            postResponse.setLiked(userReactPost == null ? false : userReactPost.isLike());
            postResponse.setDisliked(userReactPost == null ? false : userReactPost.isDislike());
            postResponse.setLikes(userLike.size());
            postResponse.setDislikes(userDislike.size());
            return postResponse;
        }
        return null;
    }


}
