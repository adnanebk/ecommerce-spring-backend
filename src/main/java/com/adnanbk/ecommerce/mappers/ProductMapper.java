package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.CategoryDto;
import com.adnanbk.ecommerce.dto.ProductDto;
import com.adnanbk.ecommerce.models.Category;
import com.adnanbk.ecommerce.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto userDto);

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
