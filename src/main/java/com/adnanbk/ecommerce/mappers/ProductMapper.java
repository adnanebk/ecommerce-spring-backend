package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.CategoryDto;
import com.adnanbk.ecommerce.dto.ProductDto;
import com.adnanbk.ecommerce.models.Category;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.utils.ImageUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected ImageUtil imageUtil;

    @Mapping(target = "images", expression = "java(imageUtil.toImagesUrlS(product.getImageNames()))")
    public abstract ProductDto toDto(Product product);

    public abstract Product toEntity(ProductDto userDto);

    public abstract CategoryDto toDto(Category category);

    public abstract Category toEntity(CategoryDto categoryDto);

}
