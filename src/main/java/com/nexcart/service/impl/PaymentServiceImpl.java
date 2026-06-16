package com.nexcart.service.impl;

import com.nexcart.dto.payment.CheckoutResponse;
import com.nexcart.entity.Order;
import com.nexcart.enums.PaymentStatus;
import com.nexcart.enums.OrderStatus;
import com.nexcart.repository.OrderRepository;
import com.nexcart.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @Override
    public CheckoutResponse createCheckoutSession(Long orderId) {

        try {

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // Prevent creating another checkout session for a paid order
            if (order.getPaymentStatus() == PaymentStatus.PAID) {
                throw new RuntimeException("Order has already been paid.");
            }

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)

                    // Stripe will redirect here after payment
                    .setSuccessUrl("http://localhost:4200/payment-success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("http://localhost:4200/payment-cancel")

                    // Easier way to identify the order
                    .setClientReferenceId(order.getId().toString())

                    // Metadata used by the webhook
                    .putMetadata("orderId", order.getId().toString())

                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("lkr")
                                                    .setUnitAmount(
                                                            order.getTotalAmount()
                                                                    .multiply(java.math.BigDecimal.valueOf(100))
                                                                    .longValueExact()
                                                    )
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Order #" + order.getId())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);

            System.out.println("Created Stripe Session : " + session.getId());

            order.setStripeSessionId(session.getId());
            order.setPaymentStatus(PaymentStatus.PENDING);

            orderRepository.save(order);

            return new CheckoutResponse(
                    session.getId(),
                    session.getUrl()
            );

        } catch (Exception e) {
            throw new RuntimeException("Unable to create Stripe Checkout Session.", e);
        }
    }

    @Override
    public void handleWebhook(String payload, String signature) {

        try {

            Event event = Webhook.constructEvent(
                    payload,
                    signature,
                    webhookSecret
            );

            System.out.println("Event Type = " + event.getType());

            if (!"checkout.session.completed".equals(event.getType())) {
                return;
            }

            // Parse the raw webhook JSON
            com.google.gson.JsonObject json =
                    com.google.gson.JsonParser
                            .parseString(payload)
                            .getAsJsonObject();

            String sessionId =
                    json.getAsJsonObject("data")
                            .getAsJsonObject("object")
                            .get("id")
                            .getAsString();

            System.out.println("Session ID = " + sessionId);

            // Retrieve the full Checkout Session from Stripe
            Session session = Session.retrieve(sessionId);

            String orderIdValue = session.getMetadata().get("orderId");

            System.out.println("Order ID = " + orderIdValue);

            Order order = orderRepository.findById(
                            Long.valueOf(orderIdValue)
                    )
                    .orElseThrow(() ->
                            new RuntimeException("Order not found"));

            order.setPaymentStatus(PaymentStatus.PAID);
            order.setStatus(OrderStatus.CONFIRMED);

            orderRepository.save(order);

            System.out.println("Order updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}