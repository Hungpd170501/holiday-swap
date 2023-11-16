package com.example.holidayswap.domain.dto.response.post;

import com.example.holidayswap.domain.entity.auth.User;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String content;
    private Date datePosted;
    private String userName;
    private String avatar;
    private int likes;
    private int dislikes;
    private boolean isLiked;
    private boolean isDisliked;
}
