package com.jt.e_commerce_website_backend.model;

import lombok.Data;

@Data
public class BankDetails {

    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
}
