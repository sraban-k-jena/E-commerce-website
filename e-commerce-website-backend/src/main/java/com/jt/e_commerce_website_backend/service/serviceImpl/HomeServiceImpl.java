package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.constant.HomeCategorySection;
import com.jt.e_commerce_website_backend.model.Deal;
import com.jt.e_commerce_website_backend.model.Home;
import com.jt.e_commerce_website_backend.model.HomeCategory;
import com.jt.e_commerce_website_backend.repository.DealRepository;
import com.jt.e_commerce_website_backend.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    public DealRepository dealRepository;

    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {

        List<HomeCategory> gridCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.GRID).collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> electricCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.DEALS).collect(Collectors.toList());

        List<Deal> creatDeals = new ArrayList<>();

        if (dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream()
                    .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                    .map(category -> new Deal(10, category))
                    .collect(Collectors.toList());

            creatDeals = dealRepository.saveAll(deals);

        } else {
            creatDeals = dealRepository.findAll();
        }

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(creatDeals);
        home.setDealCategories(dealCategories);

        return home;
    }

}
