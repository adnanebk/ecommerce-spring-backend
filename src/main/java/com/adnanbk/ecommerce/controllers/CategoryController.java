package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.dto.CategoryDto;
import com.adnanbk.ecommerce.mappers.CategoryMapper;
import com.adnanbk.ecommerce.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    Iterable<CategoryDto> getAll() {
        return categoryService.getAll().stream()
                .map(categoryMapper::toDto).toList();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    CategoryDto create(@RequestBody @Valid CategoryDto categoryDto) {
        return Optional.of(categoryDto)
                .map(categoryMapper::toEntity)
                .map(categoryService::create)
                .map(categoryMapper::toDto).orElseThrow();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    CategoryDto update(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto) {
        return Optional.of(categoryDto)
                .map(categoryMapper::toEntity)
                .map(cat -> categoryService.update(id, cat))
                .map(categoryMapper::toDto).orElseThrow();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void remove(@PathVariable Long id) {
        categoryService.remove(id);
    }
}
