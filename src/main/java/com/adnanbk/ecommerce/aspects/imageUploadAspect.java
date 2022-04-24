package com.adnanbk.ecommerce.aspects;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.models.Product;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class imageUploadAspect {

    @Value("${imagesPathUrl}")
    private String imagesPathUrl;

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.findBySku(..))",returning = "product")
    public void findByProductSkuAspect(Optional<Product> product) {
        product.ifPresent(prod->prod.setImage(imagesPathUrl + prod.getImage()));
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.find*(..,org.springframework.data.domain.Pageable))",returning = "products")
    public void findAllProductsAspect(Page<Product>  products) {
        System.out.println("aspect");
        products.forEach(product -> product.setImage(imagesPathUrl+product.getImage()));
    }


    @Around(value = "execution(* com.adnanbk.ecommerce.services.imp.ProductServiceImp.updateProducts(..))")
    public List<Product> updateProductsAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        var productsArg = (List<Product>) joinPoint.getArgs()[0];
        productsArg.forEach(product -> product.setImage(product.getImage().replace(imagesPathUrl,"")));

        var products = (List<Product>) joinPoint.proceed(new Object[]{productsArg});
        products.forEach(product -> product.setImage(imagesPathUrl+product.getImage()));
        return products;
    }
    @Around(value = "execution(* com.adnanbk.ecommerce.services.imp.ProductServiceImp.updateProduct(..))")
    public Product updateProductAspect(ProceedingJoinPoint joinPoint) throws Throwable {
       var productArg = (Product) joinPoint.getArgs()[0];
        productArg.setImage(productArg.getImage().replace(imagesPathUrl,""));
         Product product = (Product) joinPoint.proceed(new Object[]{productArg});
        product.setImage(imagesPathUrl+product.getImage());
        return product;
    }
    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.services.imp.ProductServiceImp.addProduct(..))",returning = "product")
    public void addProductAspect(Product  product) {
        product.setImage(imagesPathUrl+product.getImage());
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.services.imp.AuthServiceImp.changeUserImage(..))",returning = "imageDto")
    public void updateUserImageAspect(ImageDto imageDto) {
        imageDto.setUrl(imagesPathUrl+imageDto.getUrl());
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.services.imp.AuthServiceImp.handleLogin*(..))",returning = "jwtDto")
    public void loginAspect(JwtDto jwtDto) {
        var userImage = jwtDto.getAppUser().getImageUrl();
        if(userImage!=null && !jwtDto.getAppUser().getImageUrl().contains("http"))
        jwtDto.getAppUser().setImageUrl(imagesPathUrl+jwtDto.getAppUser().getImageUrl());
    }
}
