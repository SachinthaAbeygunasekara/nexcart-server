package com.nexcart.controller;

import com.nexcart.dto.cart.CartItemRequest;
import com.nexcart.dto.cart.CartResponse;
import com.nexcart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponse getCart() {
        return cartService.getCart();
    }

    @PostMapping("/items")
    public CartResponse addItem(
            @RequestBody CartItemRequest request) {

        return cartService.addItem(request);
    }

    @PutMapping("/items/{id}")
    public CartResponse updateItem(
            @PathVariable Integer id,
            @RequestParam Integer quantity) {

        return cartService.updateItem(
                id,
                quantity
        );
    }

    @DeleteMapping("/items/{id}")
    public void removeItem(
            @PathVariable Integer id) {

        cartService.removeItem(id);
    }

    @DeleteMapping
    public void clearCart() {

        cartService.clearCart();
    }
}
