package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.Deal;
import com.jt.e_commerce_website_backend.model.HomeCategory;
import com.jt.e_commerce_website_backend.repository.DealRepository;
import com.jt.e_commerce_website_backend.repository.HomeCategoryRepository;
import com.jt.e_commerce_website_backend.service.DealService;

@Service
public class DealServiceImpl implements DealService {

    @Autowired
    public DealRepository dealRepository;

    @Autowired
    public HomeCategoryRepository homeCategoryRepository;

    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
        Deal deal2 = dealRepository.save(deal);
        deal2.setCategory(category);
        deal2.setDiscount(deal.getDiscount());

        return dealRepository.save(deal2);
    }

    @Override
    public Deal updateDeal(Deal deal, long id) throws Exception {
        Deal existDeal = dealRepository.findById(id).orElse(null);
        HomeCategory homeCategory = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
        if (existDeal != null) {
            if (deal.getDiscount() != 0) {
                existDeal.setDiscount(deal.getDiscount());
            }
            if (homeCategory != null) {
                existDeal.setCategory(homeCategory);
            }
            return dealRepository.save(existDeal);
        }
        throw new Exception("deal not found");
    }

    @Override
    public void deleteDeal(long id) throws Exception {
        Deal deal = dealRepository.findById(id).orElseThrow(() -> new Exception("deal not found ."));
        dealRepository.delete(deal);
    }

}
