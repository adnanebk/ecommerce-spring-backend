package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category create(Category category);

    Category update(Long id, Category category);

    void remove(Long id);
}
