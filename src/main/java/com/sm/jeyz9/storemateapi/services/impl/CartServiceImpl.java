package com.sm.jeyz9.storemateapi.services.impl;

import com.sm.jeyz9.storemateapi.dto.CartItemRequestDTO;
import com.sm.jeyz9.storemateapi.exceptions.WebException;
import com.sm.jeyz9.storemateapi.models.Cart;
import com.sm.jeyz9.storemateapi.models.CartItem;
import com.sm.jeyz9.storemateapi.models.CartStatusName;
import com.sm.jeyz9.storemateapi.models.Product;
import com.sm.jeyz9.storemateapi.models.User;
import com.sm.jeyz9.storemateapi.repository.CartItemRepository;
import com.sm.jeyz9.storemateapi.repository.CartRepository;
import com.sm.jeyz9.storemateapi.repository.ProductRepository;
import com.sm.jeyz9.storemateapi.repository.UserRepository;
import com.sm.jeyz9.storemateapi.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    @Transactional
    public String addProductToCart(String email, CartItemRequestDTO request) {
        try{
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "User not found."));
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Product not found."));
            if(product.getStock_quantity() < request.getQuantity()) throw new WebException(HttpStatus.BAD_REQUEST, "There is insufficient stock.");

            Cart cart = cartRepository.findCartByStatus(CartStatusName.ACTIVE).orElse(null);
            if(cart == null) {
                Cart newCart = Cart.builder()
                        .id(null)
                        .user(user)
                        .status(CartStatusName.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                cart = cartRepository.save(newCart);
            }
            
            CartItem cartItem = cartItemRepository.findCartItemByIdAndCartId(cart.getId(), request.getProductId()).orElse(null);
            if(cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
                cartItem.setUpdatedAt(LocalDateTime.now());
            }else{
                cartItem = CartItem.builder()
                        .id(null)
                        .cart(cart)
                        .product(product)
                        .createdAt(LocalDateTime.now())
                        .quantity(request.getQuantity())
                        .updatedAt(LocalDateTime.now())
                        .build();
            }
            
            cartItemRepository.save(cartItem);
            
            product.setStock_quantity(product.getStock_quantity() - cartItem.getQuantity());
            productRepository.save(product);
            
            return "Add product to cart success";
        } catch (WebException e) {
            throw e;
        }catch(Exception e) {
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error " + e.getMessage());
        }
        
    }
}
