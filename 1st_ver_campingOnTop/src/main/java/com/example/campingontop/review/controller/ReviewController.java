package com.example.campingontop.review.controller;

import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.model.ReviewUpdateDTO;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.review.service.ReviewService;
import com.example.campingontop.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // 모든 리뷰 조회
    @GetMapping("/list")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // 특정 ID의 리뷰 조회
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .orElseThrow(() -> new IllegalStateException("Review not found"));
    }

    @GetMapping("/average-rating")
    public Double getAverageRating() {
        return reviewService.getAverageRating();
    }

    // 최신 순 리뷰 조회
    @GetMapping("/newest")
    public List<Review> getReviewsByNewest() {
        return reviewService.getReviewsByNewest();
    }

    // 별점 높은 순 리뷰 조회
    @GetMapping("/highest-rating")
    public List<Review> getReviewsByHighestRating() {
        return reviewService.getReviewsByHighestRating();
    }

    // 별점 낮은 순 리뷰 조회
    @GetMapping("/lowest-rating")
    public List<Review> getReviewsByLowestRating() {
        return reviewService.getReviewsByLowestRating();
    }


    @PostMapping("/create")
    public ResponseEntity createReview(@AuthenticationPrincipal User user,
                                       @RequestPart PostCreateReviewDtoReq postCreateReviewDtoReq) {

        return ResponseEntity.ok().body(reviewService.createReview(user, postCreateReviewDtoReq));
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id,@RequestPart ReviewUpdateDTO reviewUpdateDTO,
            MultipartFile[] uploadFiles
    ) throws IOException {
        return ResponseEntity.ok().body(reviewService.updateReview(id, reviewUpdateDTO, uploadFiles));
    }


    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
