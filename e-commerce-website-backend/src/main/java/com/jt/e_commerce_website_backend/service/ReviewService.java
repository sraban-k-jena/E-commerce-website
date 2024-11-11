package com.jt.e_commerce_website_backend.service;

import java.util.List;

import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.Review;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.request.CreateReviewRequest;

public interface ReviewService {

    Review createReview(CreateReviewRequest req, User user, Product product);

    List<Review> getReviewByProductId(long productId);

    Review updateReview(long reviewId, String reviewText, double rating, long userId) throws Exception;

    void deleteReview(long reviewId, long userId) throws Exception;

    Review getReviewById(long reviewId) throws Exception;
}
