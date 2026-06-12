package com.nexcart.controller;

import com.nexcart.dto.category.CategoryRequest;
import com.nexcart.dto.category.CategoryResponse;
import com.nexcart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/admin/categories")
    public CategoryResponse createCategory(
            @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/admin/categories/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/admin/categories/{id}")
    public void deleteCategory(
            @PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }
}