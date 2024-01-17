package com.example.campingontop.review.model.response;

import com.example.campingontop.image.model.Image;
import com.example.campingontop.review.model.Review;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateReviewDtoRes {
    private String content;
    private int rating;
    private List<Image> ImageList;
    private Date createdAt;

    public static PostCreateReviewDtoRes toDto(Review review) {
        return PostCreateReviewDtoRes.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .ImageList(review.getImageList())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

