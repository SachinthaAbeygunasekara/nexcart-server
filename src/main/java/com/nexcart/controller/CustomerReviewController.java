package com.nexcart.controller;

import com.nexcart.dto.review.CreateReviewRequest;
import com.nexcart.dto.review.ReviewResponse;
import com.nexcart.dto.review.UpdateReviewRequest;
import com.nexcart.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final ReviewService reviewService;

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable Integer productId,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateReviewRequest request
    ) {

        return ResponseEntity.ok(
                reviewService.addReview(
                        productId,
                        userDetails.getUsername(),
                        request
                )
        );
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateReviewRequest request
    ) {

        return ResponseEntity.ok(
                reviewService.updateReview(
                        reviewId,
                        userDetails.getUsername(),
                        request
                )
        );
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        reviewService.deleteReview(
                reviewId,
                userDetails.getUsername()
        );

        return ResponseEntity.noContent().build();
    }

}