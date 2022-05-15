package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {



    public  void mapProductProperties(Product productSrc, Product productDest) {
        BeanUtils.copyProperties(productSrc,productDest);
    }

    public  void mapProducts(List<Product> productsSrc, List<Product> productsDest) {


        productsDest.forEach(productDest->
                mapProductProperties(productsSrc.stream().filter(productSrc->productSrc.getId().equals(productDest.getId())).findFirst().orElseThrow(),
                           productDest)
        );


    }



}
