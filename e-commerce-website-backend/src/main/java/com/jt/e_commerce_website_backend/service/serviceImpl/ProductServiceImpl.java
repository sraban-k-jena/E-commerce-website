package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jt.e_commerce_website_backend.model.Category;
import com.jt.e_commerce_website_backend.model.Product;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.repository.CategoryRepository;
import com.jt.e_commerce_website_backend.repository.ProductRepository;
import com.jt.e_commerce_website_backend.request.CreateProductRequest;
import com.jt.e_commerce_website_backend.service.ProductService;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product createProduct(CreateProductRequest req, Seller seller) {
        Category category1 = getOrCreateCategory(req.getCategory(), 1, null);
        Category category2 = getOrCreateCategory(req.getCategory2(), 2, category1);
        Category category3 = getOrCreateCategory(req.getCategory3(), 3, category2);

        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());

        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setTitle(req.getTitle());
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setColor(req.getColor());
        product.setSellingPrice(req.getSellingPrice());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSizes());
        product.setImages(req.getImages());
        product.setDiscountPercent(discountPercentage);

        return productRepository.save(product);
    }

    private Category getOrCreateCategory(String categoryId, int level, Category parent) {
        if (categoryId == null)
            return null;
        Category category = categoryRepository.findByCategoryId(categoryId);
        return category != null ? category : categoryRepository.save(new Category(categoryId, level, parent));
    }

    private int calculateDiscountPercentage(Integer mrpPrice, int sellingPrice) {
        if (mrpPrice == null || mrpPrice <= 0) {
            throw new IllegalArgumentException("MRP Price must be greater than 0");
        }
        double discount = mrpPrice - sellingPrice;
        return (int) ((discount / mrpPrice) * 100);
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = findProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(long productId, Product product) {
        findProductById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.searchProducts(query);
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, Integer sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, int pageNumber) {

        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), category));
            }

            if (colors != null && !colors.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("color"), colors));
            }

            if (sizes != null && sizes > 0) {
                predicates.add(criteriaBuilder.equal(root.get("sizes"), sizes));
            }

            if (minPrice != null && minPrice > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
            }

            if (maxPrice != null && maxPrice > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
            }

            if (minDiscount != null && minDiscount > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"), minDiscount));
            }

            if (stock != null) {
                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("sellingPrice").ascending());
        if (sort != null) {
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber, 10, Sort.by("sellingPrice").ascending());
                case "price_high" -> PageRequest.of(pageNumber, 10, Sort.by("sellingPrice").descending());
                default -> PageRequest.of(pageNumber, 10);
            };
        }

        return productRepository.findAll(spec, pageable);
    }

    @Override
    public List<Product> getProductBySellerId(long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }
}
