package com.nexcart.repository;

import com.nexcart.entity.Cart;
import com.nexcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository
        extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser(User user);
}