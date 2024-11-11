package com.jt.e_commerce_website_backend.model;

import java.util.List;

import lombok.Data;

@Data
public class Home {

    private List<HomeCategory> grid;

    private List<HomeCategory> shopByCategories;

    private List<HomeCategory> electricCategories;

    private List<HomeCategory> dealCategories;

    private List<Deal> deals;
}
