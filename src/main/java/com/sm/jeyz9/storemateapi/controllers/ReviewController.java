package com.sm.jeyz9.storemateapi.controllers;

import com.sm.jeyz9.storemateapi.dto.ReviewRequestDTO;
import com.sm.jeyz9.storemateapi.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "เพิ่มรีวิวสินค้า")
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<String> addReview(
            @PathVariable Long productId,
            @Valid @RequestBody ReviewRequestDTO request,
            Principal principal // รับ User ที่ทำการ Login อยู่ปัจจุบัน
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.addReview(productId, principal.getName(), request));
    }

    @Operation(summary = "แก้ไขรีวิวสินค้า")
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequestDTO request,
            Principal principal
    ) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, principal.getName(), request));
    }

    @Operation(summary = "ลบรีวิวสินค้า")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId,
            Principal principal
    ) {
        return ResponseEntity.ok(reviewService.deleteReview(reviewId, principal.getName()));
    }
}