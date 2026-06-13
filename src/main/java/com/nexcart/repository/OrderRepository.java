package com.nexcart.repository;

import com.nexcart.entity.Order;
import com.nexcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByCustomer(User customer);

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByCustomerOrderByCreatedAtDesc(User customer);
}