package com.nexcart.controller;

import com.nexcart.dto.payment.CheckoutResponse;
import com.nexcart.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/checkout/{orderId}")
    public ResponseEntity<CheckoutResponse> createCheckout(
            @PathVariable Long orderId
    ) {

        return ResponseEntity.ok(
                paymentService.createCheckoutSession(orderId)
        );
    }
}