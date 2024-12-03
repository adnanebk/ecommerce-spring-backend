package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.dto.CategoryDto;
import com.adnanbk.ecommerce.dto.ProductDto;
import com.adnanbk.ecommerce.models.Category;
import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.utils.ImageUtil;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected ImageUtil imageUtil;

    abstract ProductDto toDtoMapping(Product product);

    public abstract Product toEntity(ProductDto userDto);

    public abstract CategoryDto toDto(Category category);

    public abstract Category toEntity(CategoryDto categoryDto);

    public ProductDto toDto(Product product){
       ProductDto productDto =  toDtoMapping(product);
       productDto.setImages(imageUtil.toImagesUrlS(product.getImageNames()));
       return  productDto;
    }

}
