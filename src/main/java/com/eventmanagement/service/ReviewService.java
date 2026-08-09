package com.eventmanagement.service;

import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Review;
import com.eventmanagement.entity.User;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.repository.ReviewRepository;
import com.eventmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Review operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Review createReview(Long eventId, Long reviewerId, Integer rating, String comment) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        
        User reviewer = userRepository.findById(reviewerId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + reviewerId));
        
        // Check if review already exists
        if (reviewRepository.findByEventIdAndReviewerId(eventId, reviewerId).isPresent()) {
            throw new IllegalArgumentException("Review already exists for this event by this user");
        }
        
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        Review review = new Review();
        review.setEvent(event);
        review.setReviewer(reviewer);
        review.setRating(rating);
        review.setComment(comment);
        
        return reviewRepository.save(review);
    }

    public List<Review> getEventReviews(Long eventId) {
        return reviewRepository.findByEventIdOrderByCreatedAtDesc(eventId);
    }

    public List<Review> getUserReviews(Long userId) {
        return reviewRepository.findByReviewerIdOrderByCreatedAtDesc(userId);
    }

    public Double getEventAverageRating(Long eventId) {
        Double average = reviewRepository.getAverageRatingByEventId(eventId);
        return average != null ? average : 0.0;
    }

    public Integer getEventReviewCount(Long eventId) {
        return reviewRepository.countReviewsByEventId(eventId);
    }

    public void updateReview(Long reviewId, Integer rating, String comment) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        reviewRepository.delete(review);
    }

    public void markHelpful(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        review.setHelpfulCount(review.getHelpfulCount() + 1);
        reviewRepository.save(review);
    }
}
