package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.models.Product;
import com.adnanbk.ecommerce.reposetories.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductCategoryRepository categoryRepository;


    public  void mapProduct(Product productSrc, Product productDest) {
        BeanUtils.copyProperties(productSrc,productDest);
       // productDest.setCategory(categoryRepository.findById(productSrc.getCategory().getId()).get());
    }

    public  void mapProducts(List<Product> productsSrc, List<Product> productsDest) {


        productsDest.forEach(productDest->
                mapProduct(productsSrc.stream().filter(productSrc->productSrc.getId().equals(productDest.getId())).findFirst().orElseThrow(),
                           productDest)
        );


    }



}
