package com.nexcart.dto.cart;

import java.util.List;

public record CartResponse(
        List<CartItemResponse> items,
        Double total
) {}