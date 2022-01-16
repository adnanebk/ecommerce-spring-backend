package com.adnanbk.ecommerce.mappers;

import com.adnanbk.ecommerce.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ProductMapper {

    @Value("${api.url}")
    private String baseUrl;


    public void mapProduct(Product productSrc, Product productDest) {
        productDest.setCategory(productSrc.getCategory());
        productDest.setImage(productSrc.getImage());
        productDest.setSku(productSrc.getSku());
        productDest.setName(productSrc.getName());
        productDest.setDescription(productSrc.getDescription());
        productDest.setUnitPrice(productSrc.getUnitPrice());
        productDest.setActive(productSrc.isActive());
        productDest.setUnitsInStock(productSrc.getUnitsInStock());
        mapProductImage(productDest);
    }

    public void mapProducts(List<Product> productsSrc, List<Product> productsDest) {
        Collections.sort(productsSrc, Comparator.comparing(Product::getId));
        Collections.sort(productsDest, Comparator.comparing(Product::getId));

        for (int i = 0; i < productsDest.size(); i++) {
            Product productInDb = productsDest.get(i);
            Product productSrc = productsSrc.get(i);
            if (productInDb.getId().equals(productSrc.getId()))
                mapProduct(productSrc, productInDb);

        }
    }

    public Product mapProductImage(Product productSrc) {
        if (!productSrc.getImage().startsWith("http") && !productSrc.getImage().startsWith("assets"))
            productSrc.setImage(baseUrl + "/products/images/" + productSrc.getImage());
        return productSrc;
    }

}
