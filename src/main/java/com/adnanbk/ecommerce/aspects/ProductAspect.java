package com.adnanbk.ecommerce.aspects;

import com.adnanbk.ecommerce.models.Product;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class ProductAspect {

    @Value("${imagesPathUrl}")
    private String imagesPathUrl;

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.findById(..))",returning = "product")
    public void findByAspect(Optional<Product> product) {
        product.ifPresent(prod->prod.setImage(imagesPathUrl + prod.getImage()));
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.find*(..,org.springframework.data.domain.Pageable))",returning = "products")
    public void findAllAspect(Page<Product>  products) {

        products.forEach(product -> product.setImage(imagesPathUrl+product.getImage()));
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.saveAll*(..))",returning = "products")
    public void saveAllAllAspect(List<Product> products) {
        products.forEach(product -> product.setImage(imagesPathUrl+product.getImage()));
    }
    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.save(..))",returning = "product")
    public void saveAspect(Product  product) {

        product.setImage(imagesPathUrl+product.getImage());
    }

}
