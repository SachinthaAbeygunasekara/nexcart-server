package com.nexcart.repository;

import com.nexcart.entity.Order;
import com.nexcart.entity.User;
import com.nexcart.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByCustomer(User customer);

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByCustomerOrderByCreatedAtDesc(User customer);

    long countByStatus(OrderStatus status);

    long count();

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.status = 'DELIVERED'
            ORDER BY o.createdAt
            """)
    List<Order> findDeliveredOrders();

    Optional<Order> findByStripeSessionId(String stripeSessionId);
}