package com.jt.e_commerce_website_backend.request;

import java.util.List;

import lombok.Data;

@Data
public class CreateReviewRequest {
    private String reviewText;
    private double reviewRating;
    private List<String> productImages;
}
