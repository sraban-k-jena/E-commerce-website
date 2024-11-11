package com.jt.e_commerce_website_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @NotNull
    @Column(unique = true)
    private String categoryId;

    @ManyToOne
    private Category parentCategory;

    @NotNull
    private int level;

    // Custom constructor for ease of use in ProductServiceImpl
    public Category(String categoryId, int level, Category parentCategory) {
        this.categoryId = categoryId;
        this.level = level;
        this.parentCategory = parentCategory;
    }
}
