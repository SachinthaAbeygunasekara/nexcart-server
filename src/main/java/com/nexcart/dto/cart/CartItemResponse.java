package com.nexcart.dto.cart;

public record CartItemResponse(
        Integer id,
        Integer productId,
        String productName,
        String imageUrl,
        Double price,
        Integer quantity,
        Double subtotal
) {}