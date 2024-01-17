package com.example.campingontop.review.model.response;

import com.example.campingontop.reviewImage.model.ReviewImage;
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
    private List<ReviewImage> reviewImageList;
    private Date createdAt;

    public static PostCreateReviewDtoRes toDto(Review review) {
        return PostCreateReviewDtoRes.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .reviewImageList(review.getReviewImageList())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

