package com.sm.jeyz9.storemateapi.controllers;

import com.sm.jeyz9.storemateapi.dto.CartItemRequestDTO;
import com.sm.jeyz9.storemateapi.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    private final CartService cartService;
    
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @PostMapping("/cart/items")
    public ResponseEntity<String> addProductToCart(@Valid @RequestBody CartItemRequestDTO request, Principal principal) {
           return new ResponseEntity<>(cartService.addProductToCart(principal.getName(), request), HttpStatus.CREATED);
    }
}
