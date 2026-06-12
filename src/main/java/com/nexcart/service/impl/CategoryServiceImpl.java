package com.nexcart.service.impl;

import com.nexcart.dto.category.CategoryRequest;
import com.nexcart.dto.category.CategoryResponse;
import com.nexcart.entity.Category;
import com.nexcart.repository.CategoryRepository;
import com.nexcart.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse createCategory(
            CategoryRequest request) {

        Category category =
                modelMapper.map(
                        request,
                        Category.class
                );

        Category savedCategory =
                categoryRepository.save(category);

        return modelMapper.map(
                savedCategory,
                CategoryResponse.class
        );
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category ->
                        modelMapper.map(
                                category,
                                CategoryResponse.class
                        )
                )
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(
            Integer id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Category not found with id: " + id
                                ));

        return modelMapper.map(
                category,
                CategoryResponse.class
        );
    }

    @Override
    public CategoryResponse updateCategory(
            Integer id,
            CategoryRequest request) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Category not found with id: " + id
                                ));

        category.setName(
                request.getName()
        );

        Category updatedCategory =
                categoryRepository.save(category);

        return modelMapper.map(
                updatedCategory,
                CategoryResponse.class
        );
    }

    @Override
    public void deleteCategory(
            Integer id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Category not found with id: " + id
                                ));

        categoryRepository.delete(category);
    }
}