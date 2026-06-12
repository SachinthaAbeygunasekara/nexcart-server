package com.nexcart.repository;

import com.nexcart.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Integer> {
}