package com.jt.e_commerce_website_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.e_commerce_website_backend.model.Deal;
import com.jt.e_commerce_website_backend.response.ApiResponse;
import com.jt.e_commerce_website_backend.service.DealService;

@RestController
@RequestMapping("/admin/deals")
public class DealController {

    @Autowired
    public DealService dealService;

    @PostMapping
    public ResponseEntity<Deal> createDeals(@RequestBody Deal deal) {
        Deal createdDeals = dealService.createDeal(deal);
        return new ResponseEntity<>(createdDeals, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable long id, @RequestBody Deal deal) throws Exception {
        Deal updateDeal = dealService.updateDeal(deal, id);
        return ResponseEntity.ok(updateDeal);
    }

    @DeleteMapping("/id")
    public ResponseEntity<ApiResponse> deleteDeals(@PathVariable long id) throws Exception {
        dealService.deleteDeal(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Deal Deleted .");

        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
