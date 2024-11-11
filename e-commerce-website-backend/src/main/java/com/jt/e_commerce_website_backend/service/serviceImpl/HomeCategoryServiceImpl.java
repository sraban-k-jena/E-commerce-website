package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jt.e_commerce_website_backend.model.HomeCategory;
import com.jt.e_commerce_website_backend.repository.HomeCategoryRepository;
import com.jt.e_commerce_website_backend.service.HomeCategoryService;

@Service
public class HomeCategoryServiceImpl implements HomeCategoryService {

    @Autowired
    public HomeCategoryRepository homeCategoryRepository;

    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createHomeCategories(List<HomeCategory> homeCategories) {
        if (homeCategoryRepository.findAll().isEmpty()) {
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory category, long id) throws Exception {

        HomeCategory existingCategory = homeCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found ."));

        if (category.getImage() != null) {
            existingCategory.setImage(category.getImage());
        }

        if (category.getCategoryId() != null) {
            existingCategory.setCategoryId(category.getCategoryId());
        }

        return homeCategoryRepository.save(existingCategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }

}
