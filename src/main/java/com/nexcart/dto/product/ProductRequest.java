package com.nexcart.dto.product;

import com.nexcart.entity.Category;
import com.nexcart.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;
    private Integer quantity;
    private ProductStatus status;
}