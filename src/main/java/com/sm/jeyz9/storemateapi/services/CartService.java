package com.sm.jeyz9.storemateapi.services;

import com.sm.jeyz9.storemateapi.dto.CartItemRequestDTO;

public interface CartService {
    String addProductToCart(String email, CartItemRequestDTO request);
}
