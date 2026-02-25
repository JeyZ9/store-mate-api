package com.sm.jeyz9.storemateapi.services.impl;

import com.sm.jeyz9.storemateapi.dto.ReviewRequestDTO;
import com.sm.jeyz9.storemateapi.exceptions.WebException;
import com.sm.jeyz9.storemateapi.models.Product;
import com.sm.jeyz9.storemateapi.models.Review;
import com.sm.jeyz9.storemateapi.models.User;
import com.sm.jeyz9.storemateapi.repository.ProductRepository;
import com.sm.jeyz9.storemateapi.repository.ReviewRepository;
import com.sm.jeyz9.storemateapi.repository.UserRepository;
import com.sm.jeyz9.storemateapi.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String addReview(Long productId, String userEmail, ReviewRequestDTO request) {
        try{
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "User not found."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Product not found."));

        // (Optional) อาจจะเพิ่ม Logic ตรวจสอบว่า User เคยซื้อสินค้านี้จริงๆ ผ่าน OrderRepository ก่อนให้รีวิว

        Review review = Review.builder()
                .reviewer(user)
                .product(product)
                .reviewScore(request.getReviewScore())
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
        return "Review added successfully.";
        }catch(WebException e) {
            throw e;
        }
        catch (Exception e) {
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String updateReview(Long reviewId, String userEmail, ReviewRequestDTO request) {
        try{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Review not found."));

        // ตรวจสอบว่าเป็นเจ้าของรีวิวหรือไม่
        if (!review.getReviewer().getEmail().equals(userEmail)) {
            throw new WebException(HttpStatus.FORBIDDEN, "You do not have permission to update this review.");
        }

        review.setReviewScore(request.getReviewScore());
        review.setMessage(request.getMessage());
        // อาจจะมีการเก็บ updatedAt เพิ่มเติมถ้าใน Entity มี

        reviewRepository.save(review);
        return "Review updated successfully.";
        }catch (Exception e) {
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String deleteReview(Long reviewId, String userEmail) {
        try{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Review not found."));

        // ตรวจสอบว่าเป็นเจ้าของรีวิวหรือไม่ (อาจจะอนุญาตให้ Role ADMIN ลบได้ด้วยก็ได้)
        if (!review.getReviewer().getEmail().equals(userEmail)) {
            throw new WebException(HttpStatus.FORBIDDEN, "You do not have permission to delete this review.");
        }

        reviewRepository.delete(review);
        return "Review deleted successfully.";
        }catch (Exception e) {
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }
}