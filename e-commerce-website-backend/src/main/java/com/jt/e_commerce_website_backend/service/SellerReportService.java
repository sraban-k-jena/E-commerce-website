package com.jt.e_commerce_website_backend.service;

import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.model.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(Seller seller);

    SellerReport updateSellerReport(SellerReport sellerReport);
}
