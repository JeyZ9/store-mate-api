package com.sm.jeyz9.storemateapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = true, updatable = false)
    private String orderNo;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OrderStatus status;
    
    private String currency;
    
    @ManyToOne
    @JoinColumn(name = "checkout_type_id", referencedColumnName = "id")
    private CheckoutType checkoutType;
    
    private String stripeSessionId;
    private String stripePaymentIntent;
    
    @ManyToOne
    @JoinColumn(name = "order_channel_id", referencedColumnName = "id")
    private OrderChannel orderChannel;
    
    private LocalDateTime paidAt;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @PrePersist
    public void perPersist() {
        this.orderNo = UUID.randomUUID().toString();
    }
}
