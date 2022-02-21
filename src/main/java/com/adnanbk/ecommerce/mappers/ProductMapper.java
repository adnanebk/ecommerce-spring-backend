package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.models.Product;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;

public class ProductMapper {

    private ProductMapper() {
    }

    public static void mapProduct(Product productSrc, Product productDest) {
        BeanUtils.copyProperties(productSrc,productDest);
    }

    public static void mapProducts(List<Product> productsSrc, List<Product> productsDest) {
        productsSrc.sort(Comparator.comparing(Product::getId));
        productsDest.sort(Comparator.comparing(Product::getId));

        for (int i = 0; i < productsDest.size(); i++) {
            Product productInDb = productsDest.get(i);
            Product productSrc = productsSrc.get(i);
            if (productInDb.getId().equals(productSrc.getId()))
                mapProduct(productSrc, productInDb);

        }
    }



}
