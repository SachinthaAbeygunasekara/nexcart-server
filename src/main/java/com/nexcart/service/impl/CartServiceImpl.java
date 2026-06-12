package com.nexcart.service.impl;

import com.nexcart.dto.cart.CartItemRequest;
import com.nexcart.dto.cart.CartItemResponse;
import com.nexcart.dto.cart.CartResponse;
import com.nexcart.entity.Cart;
import com.nexcart.entity.CartItem;
import com.nexcart.entity.Product;
import com.nexcart.entity.User;
import com.nexcart.repository.CartItemRepository;
import com.nexcart.repository.CartRepository;
import com.nexcart.repository.ProductRepository;
import com.nexcart.repository.UserRepository;
import com.nexcart.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartResponse getCart() {
        Cart cart = getOrCreateCart();

        return mapToResponse(cart);
    }

    @Override
    public CartResponse addItem(CartItemRequest request) {

        Cart cart = getOrCreateCart();

        Product product =
                productRepository.findById(
                                request.productId()
                        )
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Product not found"
                                ));

        CartItem item =
                cart.getItems()
                        .stream()
                        .filter(i ->
                                i.getProduct()
                                        .getId()
                                        .equals(
                                                product.getId()
                                        ))
                        .findFirst()
                        .orElse(null);

        if (item != null) {

            item.setQuantity(
                    item.getQuantity()
                            + request.quantity()
            );

        } else {

            item = new CartItem();

            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(
                    request.quantity()
            );

            cart.getItems()
                    .add(item);
        }

        cartRepository.save(cart);

        return mapToResponse(cart);
    }

    @Override
    public CartResponse updateItem(Integer itemId, Integer quantity) {
        CartItem item =
                cartItemRepository.findById(itemId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Cart item not found"
                                ));

        item.setQuantity(quantity);

        cartItemRepository.save(item);

        return mapToResponse(
                item.getCart()
        );
    }

    @Override
    public void removeItem(Integer itemId) {
        cartItemRepository.deleteById(itemId);
    }

    @Override
    public void clearCart() {
        Cart cart = getOrCreateCart();

        cart.getItems().clear();

        cartRepository.save(cart);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return userRepository.findByUsername(
                authentication.getName()
        );
    }

    private Cart getOrCreateCart() {

        User user = getCurrentUser();

        return cartRepository
                .findByUser(user)
                .orElseGet(() -> {

                    Cart cart = new Cart();
                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }

    private CartResponse mapToResponse(
            Cart cart) {

        List<CartItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(item ->
                                new CartItemResponse(
                                        item.getId(),
                                        item.getProduct().getId(),
                                        item.getProduct().getName(),
                                        item.getProduct().getImageUrl(),
                                        item.getProduct().getPrice(),
                                        item.getQuantity(),
                                        item.getProduct().getPrice()
                                                * item.getQuantity()
                                ))
                        .toList();

        double total =
                items.stream()
                        .mapToDouble(
                                CartItemResponse::subtotal
                        )
                        .sum();

        return new CartResponse(
                items,
                total
        );
    }
}
