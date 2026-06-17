package com.nexcart.dto.review;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;

    private String customerName;
}