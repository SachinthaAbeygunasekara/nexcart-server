package com.nexcart.repository;

import com.nexcart.entity.Product;
import com.nexcart.entity.Review;
import com.nexcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductOrderByCreatedAtDesc(Product product);

    Optional<Review> findByCustomerAndProduct(
            User customer,
            Product product
    );

    boolean existsByCustomerAndProduct(
            User customer,
            Product product
    );

    @Query("""
            SELECT AVG(r.rating)
            FROM Review r
            WHERE r.product.id = :productId
            """)
    Double getAverageRating(Integer productId);

    @Query("""
            SELECT COUNT(r)
            FROM Review r
            WHERE r.product.id = :productId
            """)
    Long getTotalReviews(Integer productId);

    List<Review> findAllByOrderByCreatedAtDesc();
}