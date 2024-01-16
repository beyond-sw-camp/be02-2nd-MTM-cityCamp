package com.example.campingontop.review.service;


import com.example.campingontop.aws.service.S3Service;
import com.example.campingontop.image.service.ImageService;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.review.model.ReviewUpdateDTO;
import com.example.campingontop.review.model.request.PostCreateReviewDtoReq;
import com.example.campingontop.review.repository.ReviewRepository;
import com.example.campingontop.user.model.User;
import com.example.campingontop.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageService imageService;
    private final S3Service s3Service;

    @Transactional
    public Review createReview(User user, PostCreateReviewDtoReq request) {
        Review review = Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .rating(request.getRating())
                .build();

        review = reviewRepository.save(review);

        List<String> imageUrls = new ArrayList<>();

        if (request.getImages() != null && request.getImages().length > 0) {
            for (MultipartFile file : request.getImages()) {
                String savePath = ImageUtils.makeReviewImagePath(file.getOriginalFilename());
                String imageUrl = s3Service.uploadFile(file, savePath);
                imageUrls.add(imageUrl);
            }
        }

        review.setImageUrls(imageUrls);

        return review;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByNewest() {
        // 최신 순 조회 메서드를 createdAt 필드를 기준으로 정렬하도록 변경
        return reviewRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Review> getReviewsByHighestRating() {
        return reviewRepository.findAllByOrderByRatingDesc();
    }

    public Double getAverageRating() {
        return reviewRepository.findAverageRating();
    }

    public List<Review> getReviewsByLowestRating() {
        return reviewRepository.findAllByOrderByRatingAsc();
    }

    @Transactional
    public Review updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO, MultipartFile[] images) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Review review = reviewRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Review not found"));

            if (!review.getUser().getUsername().equals(userDetails.getUsername())) {
                throw new IllegalStateException("당신은 이 리뷰의 작성자가 아닙니다.");
            }   // 리뷰 작성 유저와 유저 디테일을 통해 받아온 유저가 다르면 예외처리

                // 타이틀이 비어있지 않으면 타이틀도 수정
            if (reviewUpdateDTO.getTitle() != null) {
                review.setTitle(reviewUpdateDTO.getTitle());
            }

            review.setContent(reviewUpdateDTO.getContent());
            review.setRating(reviewUpdateDTO.getRating());

            review = reviewRepository.save(review);

            if (images != null && images.length > 0) {
                imageService.createImage(review.getId(), images);
            }

            return review;
        } else {
            throw new IllegalStateException("인증된 사용자 정보를 가져올 수 없습니다.");
        }
    }


    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
