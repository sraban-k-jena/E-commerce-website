package com.jt.e_commerce_website_backend.service;

import java.util.List;

import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(Order order);

    List<Transaction> getTransactionsBySellerId(Seller seller);

    List<Transaction> getAllTransactions();
}
