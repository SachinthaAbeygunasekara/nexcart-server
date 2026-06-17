package com.nexcart.service;

import com.nexcart.dto.review.*;

import java.util.List;

public interface ReviewService {

    ReviewResponse addReview(
            Integer productId,
            String username,
            CreateReviewRequest request
    );

    ReviewResponse updateReview(
            Long reviewId,
            String username,
            UpdateReviewRequest request
    );

    void deleteReview(
            Long reviewId,
            String username
    );

    List<ReviewResponse> getProductReviews(
            Integer productId
    );

    ProductRatingResponse getProductRating(
            Integer productId
    );

    List<ReviewResponse> getAllReviews();

    void deleteReviewByAdmin(
            Long reviewId
    );
}