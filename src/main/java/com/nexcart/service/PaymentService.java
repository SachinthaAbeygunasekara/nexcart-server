package com.nexcart.service;

import com.nexcart.dto.payment.CheckoutResponse;

public interface PaymentService {

    CheckoutResponse createCheckoutSession(Long orderId);

    void handleWebhook(String payload, String signature);
}