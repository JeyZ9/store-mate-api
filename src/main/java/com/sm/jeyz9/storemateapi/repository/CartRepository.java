package com.sm.jeyz9.storemateapi.repository;

import com.sm.jeyz9.storemateapi.models.Cart;
import com.sm.jeyz9.storemateapi.models.CartStatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByStatus(CartStatusName status);
}
