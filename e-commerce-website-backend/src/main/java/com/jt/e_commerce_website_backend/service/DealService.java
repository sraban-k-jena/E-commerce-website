package com.jt.e_commerce_website_backend.service;

import java.util.List;

import com.jt.e_commerce_website_backend.model.Deal;

public interface DealService {

    List<Deal> getDeals();

    Deal createDeal(Deal deal);

    Deal updateDeal(Deal deal, long id) throws Exception;

    void deleteDeal(long id) throws Exception;
}
