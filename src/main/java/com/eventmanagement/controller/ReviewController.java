package com.eventmanagement.controller;

import com.eventmanagement.entity.Review;
import com.eventmanagement.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Review operations
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestParam Long eventId,
            @RequestParam Long reviewerId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment) {
        Review review = reviewService.createReview(eventId, reviewerId, rating, comment);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Review>> getEventReviews(@PathVariable Long eventId) {
        List<Review> reviews = reviewService.getEventReviews(eventId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getUserReviews(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/event/{eventId}/average-rating")
    public ResponseEntity<Double> getEventAverageRating(@PathVariable Long eventId) {
        Double averageRating = reviewService.getEventAverageRating(eventId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/event/{eventId}/count")
    public ResponseEntity<Integer> getEventReviewCount(@PathVariable Long eventId) {
        Integer count = reviewService.getEventReviewCount(eventId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @PathVariable Long reviewId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment) {
        reviewService.updateReview(reviewId, rating, comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{reviewId}/mark-helpful")
    public ResponseEntity<Void> markHelpful(@PathVariable Long reviewId) {
        reviewService.markHelpful(reviewId);
        return ResponseEntity.noContent().build();
    }
}
