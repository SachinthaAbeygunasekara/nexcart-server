package com.nexcart.dto.product;

import com.nexcart.entity.Category;
import com.nexcart.enums.ProductStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;
    private Integer quantity;
    private ProductStatus status;

}