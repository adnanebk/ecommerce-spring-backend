package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.CategoryDto;
import com.adnanbk.ecommerce.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
