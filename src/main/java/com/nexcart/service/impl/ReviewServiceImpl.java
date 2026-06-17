package com.nexcart.service.impl;

import com.nexcart.dto.review.*;
import com.nexcart.entity.*;
import com.nexcart.enums.OrderStatus;
import com.nexcart.repository.*;
import com.nexcart.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public ReviewResponse addReview(
            Integer productId,
            String username,
            CreateReviewRequest request
    ) {

        User customer = userRepository.findByUsername(username);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean purchased = orderRepository.existsPurchasedProduct(
                customer.getId(),
                productId,
                OrderStatus.DELIVERED
        );

        if (!purchased) {
            throw new RuntimeException("You can review only purchased products");
        }

        if (reviewRepository.existsByCustomerAndProduct(customer, product)) {
            throw new RuntimeException("You already reviewed this product");
        }

        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .customer(customer)
                .product(product)
                .build();

        reviewRepository.save(review);

        return map(review);
    }

    @Override
    public ReviewResponse updateReview(
            Long reviewId,
            String username,
            UpdateReviewRequest request
    ) {

        User customer = userRepository.findByUsername(username);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (review.getCustomer().getId() != (customer.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);

        return map(review);
    }

    @Override
    public void deleteReview(
            Long reviewId,
            String username
    ) {

        User customer = userRepository.findByUsername(username);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (review.getCustomer().getId() != (customer.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponse> getProductReviews(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return reviewRepository.findByProductOrderByCreatedAtDesc(product)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public ProductRatingResponse getProductRating(Integer productId) {

        Double average = reviewRepository.getAverageRating(productId);

        Long total = reviewRepository.getTotalReviews(productId);

        return ProductRatingResponse.builder()
                .averageRating(average == null ? 0.0 : average)
                .totalReviews(total)
                .build();
    }

    @Override
    public List<ReviewResponse> getAllReviews() {

        return reviewRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public void deleteReviewByAdmin(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewRepository.delete(review);
    }

    private ReviewResponse map(Review review) {

        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .customerName(
                        review.getCustomer().getFirstName() + " " +
                                review.getCustomer().getLastName()
                )
                .build();
    }

}