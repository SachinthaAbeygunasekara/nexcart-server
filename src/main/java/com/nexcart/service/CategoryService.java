package com.nexcart.service;

import com.nexcart.dto.category.CategoryRequest;
import com.nexcart.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(
            CategoryRequest request
    );

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(
            Integer id
    );

    CategoryResponse updateCategory(
            Integer id,
            CategoryRequest request
    );

    void deleteCategory(Integer id);
}