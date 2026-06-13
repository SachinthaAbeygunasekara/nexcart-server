package com.nexcart.dto.order;

import lombok.Data;

@Data
public class CheckoutRequest {

    private String deliveryAddress;

    private String phoneNumber;
}