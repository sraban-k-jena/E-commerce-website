package com.jt.e_commerce_website_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jt.e_commerce_website_backend.model.Home;
import com.jt.e_commerce_website_backend.model.HomeCategory;
import com.jt.e_commerce_website_backend.service.HomeCategoryService;
import com.jt.e_commerce_website_backend.service.HomeService;

@RestController
public class HomeCategoryController {

    @Autowired
    public HomeCategoryService homeCategoryService;

    @Autowired
    public HomeService homeService;

    @PostMapping("/home/categories")
    public ResponseEntity<Home> createhOmeCategories(@RequestBody List<HomeCategory> homeCategories) {
        List<HomeCategory> categories = homeCategoryService.createHomeCategories(homeCategories);
        Home home = homeService.createHomePageData(categories);
        return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategory() {
        List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable long id,
            @RequestBody HomeCategory homeCategory) throws Exception {
        HomeCategory updateCategory = homeCategoryService.updateHomeCategory(homeCategory, id);
        return ResponseEntity.ok(updateCategory);
    }
}
