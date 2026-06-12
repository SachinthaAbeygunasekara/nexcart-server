package com.nexcart.dto.cart;

public record CartItemRequest(
        Integer productId,
        Integer quantity
) {}