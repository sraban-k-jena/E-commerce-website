package com.jt.e_commerce_website_backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.Review;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.request.CreateReviewRequest;
import com.jt.e_commerce_website_backend.response.ApiResponse;
import com.jt.e_commerce_website_backend.service.ProductService;
import com.jt.e_commerce_website_backend.service.ReviewService;
import com.jt.e_commerce_website_backend.service.UserService;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    public ReviewService reviewService;

    @Autowired
    public UserService userService;

    @Autowired
    public ProductService productService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable long productId) {
        List<Review> reviews = reviewService.getReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> writeReview(@RequestBody CreateReviewRequest req, @PathVariable long productId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(productId);

        Review review = reviewService.createReview(req, user, product);
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable long reviewId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Review review = reviewService.updateReview(reviewId, req.getReviewText(), req.getReviewRating(), user.getId());

        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/reviews/reviewId")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable long reviewId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        reviewService.deleteReview(reviewId, user.getId());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review Deleted Successfully .");

        return ResponseEntity.ok(apiResponse);
    }
}
