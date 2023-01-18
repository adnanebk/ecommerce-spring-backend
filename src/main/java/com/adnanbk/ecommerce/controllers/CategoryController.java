package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.dto.CategoryDto;
import com.adnanbk.ecommerce.mappers.CategoryMapper;
import com.adnanbk.ecommerce.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

   @GetMapping
   Iterable<CategoryDto> getAll(){
       return  categoryService.getAll().stream()
               .map(categoryMapper::toDto).toList();
    }

    @PostMapping
    CategoryDto create(@RequestBody @Valid CategoryDto categoryDto){
        return Optional.of(categoryDto)
                .map(categoryMapper::toEntity)
                .map(categoryService::create)
                .map(categoryMapper::toDto).orElseThrow();
    }

    @PutMapping("/{id}")
    void update(@PathVariable Long id,@RequestBody @Valid CategoryDto categoryDto){
          Optional.of(categoryDto)
                .map(categoryMapper::toEntity)
                .ifPresent(cat->categoryService.update(id,cat));
    }

    @DeleteMapping("/{id}")
    void remove(@PathVariable Long id){
         categoryService.remove(id);
    }
}
