package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.config.JwtProvider;
import com.jt.e_commerce_website_backend.constant.AccountStatus;
import com.jt.e_commerce_website_backend.constant.UserRole;
import com.jt.e_commerce_website_backend.model.Address;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.repository.AddressRepository;
import com.jt.e_commerce_website_backend.repository.SellerRepository;
import com.jt.e_commerce_website_backend.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    public SellerRepository sellerRepository;

    @Autowired
    public JwtProvider jwtProvider;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        System.out.println("Extracted email from JWT: " + email);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExist != null) {
            throw new Exception("Seller Already exist , used different email .");
        }

        Address saveAddress = addressRepository.save(seller.getPickAddress());
        Seller newSeller = new Seller();

        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickAddress(seller.getPickAddress());
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(UserRole.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(long id) throws Exception {
        return sellerRepository.findById(id).orElseThrow(() -> new Exception("Seller not found with id " + id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new Exception("Seller not found .....");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);

        if (seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }

        if (seller.getMobile() != null) {
            existingSeller.setMobile(seller.getMobile());
        }

        if (seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }

        if (seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName() != null) {
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }

        if (seller.getBankDetails() != null &&
                seller.getBankDetails().getAccountHolderName() != null &&
                seller.getBankDetails().getIfscCode() != null &&
                seller.getBankDetails().getAccountHolderName() != null) {

            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());

            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());

            existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
        }

        if (seller.getPickAddress() != null &&
                seller.getPickAddress().getAddress() != null &&
                seller.getPickAddress().getMobile() != null &&
                seller.getPickAddress().getCity() != null &&
                seller.getPickAddress().getState() != null) {

            existingSeller.getPickAddress().setAddress(seller.getPickAddress().getAddress());
            existingSeller.getPickAddress().setCity(seller.getPickAddress().getCity());
            existingSeller.getPickAddress().setMobile(seller.getPickAddress().getMobile());
            existingSeller.getPickAddress().setPincode(seller.getPickAddress().getPincode());

        }

        if (seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(long id) throws Exception {
        Seller seller = getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(long sellerId, AccountStatus status) throws Exception {
        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }

}
