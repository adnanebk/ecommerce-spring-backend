package com.adnanbk.ecommerce.aspects;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.models.Product;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
@ConfigurationProperties(prefix = "uploads")
@Getter
@Setter
public class ImagesAspect {

    private String imagesPathUrl;
    private String externalImagesPathUrl;

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.findBySku(..))",returning = "product")
    public void findByProductSkuAspect(Optional<Product> product) {
        product.ifPresent(prod->{
            String pathUrl = getCorrectPathUrl(prod.getImage());
            prod.setImage(pathUrl);
        });
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.reposetories.ProductRepository.find*(..,org.springframework.data.domain.Pageable))",returning = "products")
    public void findAllProductsAspect(Page<Product>  products) {
        products.forEach(product -> product.setImage(getCorrectPathUrl(product.getImage())));
    }

    @Around(value = "execution(* com.adnanbk.ecommerce.services.imp.ProductServiceImp.updateProducts(..))")
    public List<Product> updateProductsAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        var productsArg = (List<Product>) joinPoint.getArgs()[0];
        productsArg.forEach(product ->
                product.setImage(product.getImage().replace(imagesPathUrl,"").replace(externalImagesPathUrl,"")));

        var products = (List<Product>) joinPoint.proceed(new Object[]{productsArg});
        products.forEach(product -> product.setImage(getCorrectPathUrl(product.getImage())));
        return products;
    }
    @Around(value = "execution(* com.adnanbk.ecommerce.services.imp.ProductServiceImp.updateProduct(..))")
    public Product updateProductAspect(ProceedingJoinPoint joinPoint) throws Throwable {
       var productArg = (Product) joinPoint.getArgs()[0];
       var id = (Long) joinPoint.getArgs()[1];
        productArg.setImage(productArg.getImage().replace(imagesPathUrl,"").replace(externalImagesPathUrl,""));
         Product product = (Product) joinPoint.proceed(new Object[]{productArg,id});
        product.setImage(getCorrectPathUrl(product.getImage()));
        return product;
    }
    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.services.imp.ProductServiceImp.addProduct(..))",returning = "product")
    public void addProductAspect(Product  product) {
        product.setImage(getCorrectPathUrl(product.getImage()));
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.services.imp.UserServiceImpl.changeUserImage(..))",returning = "imageDto")
    public void updateUserImageAspect(ImageDto imageDto) {
        imageDto.setUrl(imagesPathUrl+imageDto.getUrl());
    }

    @AfterReturning(value = "execution(* com.adnanbk.ecommerce.services.imp.AuthServiceImp.handleLogin*(..))",returning = "jwtDto")
    public void loginAspect(JwtDto jwtDto) {
        var userImage = jwtDto.getAppUser().getImageUrl();
        if (!Strings.isNullOrEmpty(userImage) && !userImage.contains("http"))
            jwtDto.getAppUser().setImageUrl(imagesPathUrl + jwtDto.getAppUser().getImageUrl());
    }


    private String getCorrectPathUrl(String imageName) {
        if(imageName.startsWith("http"))
            return imageName;
        return (imageName.contains("luv2code") ? externalImagesPathUrl : imagesPathUrl)+imageName;
    }
}
