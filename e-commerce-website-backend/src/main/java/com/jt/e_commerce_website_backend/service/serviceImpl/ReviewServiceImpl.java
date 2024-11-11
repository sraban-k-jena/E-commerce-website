package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.Review;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.repository.ReviewRepository;
import com.jt.e_commerce_website_backend.request.CreateReviewRequest;
import com.jt.e_commerce_website_backend.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    public ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRating());
        review.setProductImages(req.getProductImages());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(long reviewId, String reviewText, double rating, long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if (review.getUser().getId() == (userId)) {
            review.setReviewText(reviewText);
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        throw new Exception("You can not update this review .");
    }

    @Override
    public void deleteReview(long reviewId, long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if (review.getUser().getId() == (userId)) {
            throw new Exception("You can not delete this review .");
        }
        reviewRepository.delete(review);

    }

    @Override
    public Review getReviewById(long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("review not found"));
    }

}
