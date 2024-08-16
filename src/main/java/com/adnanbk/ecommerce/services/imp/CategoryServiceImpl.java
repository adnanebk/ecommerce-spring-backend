package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.Category;
import com.adnanbk.ecommerce.reposetories.ProductCategoryRepository;
import com.adnanbk.ecommerce.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private ProductCategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
         return categoryRepository.findById(id)
                 .map(currentCategory->{
                     currentCategory.setName(category.getName());
                    return categoryRepository.save(currentCategory);
                 }).orElseThrow();
    }

    @Override
    public void remove(Long id) {
     categoryRepository.deleteById(id);
    }
}
