package com.nexcart.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReviewRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    private String comment;
}