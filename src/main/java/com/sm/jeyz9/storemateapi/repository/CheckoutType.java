package com.sm.jeyz9.storemateapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutType extends JpaRepository<CheckoutType, Long> {
}
