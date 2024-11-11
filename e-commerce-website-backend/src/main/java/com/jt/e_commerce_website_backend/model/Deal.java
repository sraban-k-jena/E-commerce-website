package com.jt.e_commerce_website_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int discount;

    @OneToOne
    private HomeCategory category;

    public Deal(int discount, HomeCategory category) {
        this.discount = discount;
        this.category = category;
    }

}
