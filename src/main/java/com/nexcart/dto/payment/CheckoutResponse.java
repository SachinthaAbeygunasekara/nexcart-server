package com.nexcart.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponse {

    private String sessionId;
    private String checkoutUrl;
}