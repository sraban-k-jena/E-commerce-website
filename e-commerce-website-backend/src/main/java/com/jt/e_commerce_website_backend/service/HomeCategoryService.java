package com.jt.e_commerce_website_backend.service;

import java.util.List;

import com.jt.e_commerce_website_backend.model.HomeCategory;

public interface HomeCategoryService {

    HomeCategory createHomeCategory(HomeCategory homeCategory);

    List<HomeCategory> createHomeCategories(List<HomeCategory> homeCategories);

    HomeCategory updateHomeCategory(HomeCategory category, long id) throws Exception;

    List<HomeCategory> getAllHomeCategories();

}
