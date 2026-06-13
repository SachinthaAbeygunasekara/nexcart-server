package com.nexcart.service.impl;

import com.nexcart.dto.order.CheckoutRequest;
import com.nexcart.dto.order.OrderItemResponse;
import com.nexcart.dto.order.OrderResponse;
import com.nexcart.entity.*;
import com.nexcart.enums.OrderStatus;
import com.nexcart.repository.CartRepository;
import com.nexcart.repository.OrderRepository;
import com.nexcart.repository.ProductRepository;
import com.nexcart.repository.UserRepository;
import com.nexcart.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public OrderResponse checkout(String username, CheckoutRequest request) {

        User customer = userRepository.findByUsername(username);

        if (customer == null) {
            throw new RuntimeException("User not found");
        }

        Cart cart = cartRepository
                .findByUser(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;

        Order order = new Order();

        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPhoneNumber(request.getPhoneNumber());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        product.getName() + " stock not available"
                );
            }

            BigDecimal subtotal =
                    BigDecimal.valueOf(product.getPrice())
                            .multiply(
                                    BigDecimal.valueOf(cartItem.getQuantity())
                            );

            total = total.add(subtotal);

            product.setQuantity(
                    product.getQuantity()
                            - cartItem.getQuantity()
            );

            productRepository.save(product);

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(BigDecimal.valueOf(product.getPrice()))
                    .subtotal(subtotal)
                    .order(order)
                    .build();

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return map(saved);
    }

    @Override
    public List<OrderResponse> getCustomerOrders(String username) {
        User customer = userRepository.findByUsername(username);

        if (customer == null) {
            throw new RuntimeException("User not found");
        }

        return orderRepository.findByCustomerOrderByCreatedAtDesc(customer)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        return map(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getOrders(
            Integer orderId,
            OrderStatus status,
            LocalDate date
    ) {

        Specification<Order> spec =
                (root, query, cb) -> cb.conjunction();

        if (orderId != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("id"),
                                    orderId
                            )
            );
        }

        if (status != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("status"),
                                    status
                            )
            );
        }

        if (date != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    cb.function(
                                            "DATE",
                                            LocalDate.class,
                                            root.get("createdAt")
                                    ),
                                    date
                            )
            );
        }

        return orderRepository.findAll(spec)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private OrderResponse map(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .deliveryAddress(order.getDeliveryAddress())
                .phoneNumber(order.getPhoneNumber())
                .items(
                        order.getItems()
                                .stream()
                                .map(item ->
                                        OrderItemResponse.builder()
                                                .productId(item.getProduct().getId())
                                                .productName(item.getProduct().getName())
                                                .quantity(item.getQuantity())
                                                .price(item.getPrice())
                                                .subtotal(item.getSubtotal())
                                                .build()
                                )
                                .toList()
                )
                .build();
    }

    private OrderResponse convertToResponse(Order order) {

        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(item ->
                                OrderItemResponse.builder()
                                        .productId(
                                                item.getProduct().getId()
                                        )
                                        .productName(
                                                item.getProduct().getName()
                                        )
                                        .quantity(
                                                item.getQuantity()
                                        )
                                        .price(
                                                item.getPrice()
                                        )
                                        .subtotal(
                                                item.getPrice().multiply(
                                                        BigDecimal.valueOf(
                                                                item.getQuantity()
                                                        )
                                                )
                                        )
                                        .build()
                        )
                        .toList();

        return OrderResponse.builder()
                .id(
                        order.getId()
                )
                .totalAmount(
                        order.getTotalAmount()
                )
                .status(
                        order.getStatus()
                )
                .createdAt(
                        order.getCreatedAt()
                )
                .deliveryAddress(
                        order.getDeliveryAddress()
                )
                .phoneNumber(
                        order.getPhoneNumber()
                )
                .items(
                        items
                )
                .build();
    }

}
