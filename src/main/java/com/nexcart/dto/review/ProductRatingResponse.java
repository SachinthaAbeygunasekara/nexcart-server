package com.nexcart.dto.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRatingResponse {

    private Double averageRating;

    private Long totalReviews;
}