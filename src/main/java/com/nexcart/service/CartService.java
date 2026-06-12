package com.nexcart.service;

import com.nexcart.dto.cart.CartItemRequest;
import com.nexcart.dto.cart.CartResponse;

public interface CartService {

    CartResponse getCart();

    CartResponse addItem(
            CartItemRequest request
    );

    CartResponse updateItem(
            Integer itemId,
            Integer quantity
    );

    void removeItem(
            Integer itemId
    );

    void clearCart();
}