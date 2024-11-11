package com.jt.e_commerce_website_backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.request.CreateProductRequest;

public interface ProductService {

    Product createProduct(CreateProductRequest req, Seller seller);

    void deleteProduct(long productId);

    Product updateProduct(long productId, Product product);

    Product findProductById(long productId);

    List<Product> searchProducts(String query);

    Page<Product> getAllProducts(
            String category,
            String brand,
            String colors,
            Integer sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            int pageNumber);

    List<Product> getProductBySellerId(long sellerId);
}
