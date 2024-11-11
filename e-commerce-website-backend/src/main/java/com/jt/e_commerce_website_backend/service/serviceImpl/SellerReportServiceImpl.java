package com.jt.e_commerce_website_backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.model.SellerReport;
import com.jt.e_commerce_website_backend.repository.SellerReportRepository;
import com.jt.e_commerce_website_backend.repository.SellerRepository;
import com.jt.e_commerce_website_backend.service.SellerReportService;

@Service
public class SellerReportServiceImpl implements SellerReportService {

    @Autowired
    public SellerRepository sellerRepository;

    @Autowired
    public SellerReportRepository sellerReportRepository;

    @Override
    public SellerReport getSellerReport(Seller seller) {
        SellerReport sr = sellerReportRepository.findBySellerId(seller.getId());
        if (sr == null) {
            SellerReport newReport = new SellerReport();
            newReport.setSeller(seller);
            return sellerReportRepository.save(newReport);
        }
        return sr;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }

}
