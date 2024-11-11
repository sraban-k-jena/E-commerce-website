package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.Order;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.model.Transaction;
import com.jt.e_commerce_website_backend.repository.SellerRepository;
import com.jt.e_commerce_website_backend.repository.TransactionRepository;
import com.jt.e_commerce_website_backend.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    public TransactionRepository transactionRepository;

    @Autowired
    public SellerRepository sellerRepository;

    @Override
    public Transaction createTransaction(Order order) {

        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
