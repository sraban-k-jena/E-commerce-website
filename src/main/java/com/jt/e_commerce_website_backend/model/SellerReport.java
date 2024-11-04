package com.jt.e_commerce_website_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SellerReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Seller seller;

    private long totalEarning = 0L;

    private long totalSales = 0L;

    private long totalRefunds = 0L;

    private long totalTax = 0L;

    private long netEarning = 0L;

    private int totalOrders = 0;

    private int cancelOrders = 0;

    private int totalTransaction = 0;
}
