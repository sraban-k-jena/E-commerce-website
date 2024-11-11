package com.jt.e_commerce_website_backend.service;

import java.util.List;

import com.jt.e_commerce_website_backend.model.Home;
import com.jt.e_commerce_website_backend.model.HomeCategory;

public interface HomeService {
    public Home createHomePageData(List<HomeCategory> allCategories);
}
