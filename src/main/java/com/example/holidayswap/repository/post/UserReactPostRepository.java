package com.example.holidayswap.repository.post;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.post.Post;
import com.example.holidayswap.domain.entity.post.UserReactionPost;
import com.example.holidayswap.domain.entity.post.UserReactionPostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReactPostRepository extends JpaRepository<UserReactionPost, UserReactionPostId> {
    UserReactionPost findByPostIdAndUser(Long postId, User user);

    @Query("select u from UserReactionPost u where u.post = ?1 and u.isLike = true")
    List<UserReactionPost> findAllByPostAndLike(Post post);

    @Query("select u from UserReactionPost u where u.post = ?1 and u.isDislike= true")
    List<UserReactionPost> findAllByPostAndDislike(Post post);

    UserReactionPost findByUserReactionPostId(UserReactionPostId post);
}
