package com.nexcart.dto.product;

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
    private String category;
    private Integer quantity;
    private ProductStatus status;

}