package com.nexcart.controller;

import com.nexcart.dto.review.ProductRatingResponse;
import com.nexcart.dto.review.ReviewResponse;
import com.nexcart.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}/reviews")
    public List<ReviewResponse> getReviews(
            @PathVariable Integer productId
    ) {

        return reviewService.getProductReviews(productId);
    }

    @GetMapping("/{productId}/rating")
    public ProductRatingResponse getRating(
            @PathVariable Integer productId
    ) {

        return reviewService.getProductRating(productId);
    }

}