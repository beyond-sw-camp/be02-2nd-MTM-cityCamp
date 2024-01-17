package com.example.campingontop.review.controller;

import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.model.ReviewUpdateDTO;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.review.service.ReviewService;
import com.example.campingontop.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name="Review", description = "Review 숙소 리뷰 CRUD")
@Api(tags = "Review")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

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
    public ResponseEntity createReview(
            @AuthenticationPrincipal User user,
            @RequestPart PostCreateReviewDtoReq postCreateReviewDtoReq,
            @RequestPart MultipartFile[] images
    ) {
        return ResponseEntity.ok().body(reviewService.createReview(user, postCreateReviewDtoReq, images));
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
