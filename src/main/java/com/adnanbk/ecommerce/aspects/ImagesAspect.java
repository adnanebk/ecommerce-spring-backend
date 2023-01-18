package com.adnanbk.ecommerce.aspects;

import com.adnanbk.ecommerce.dto.AuthDataDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Aspect
@Component
@ConfigurationProperties(prefix = "uploads")
@Getter
@Setter
public class ImagesAspect {

    private String imagesPathUrl;
    private String externalImagesPathUrl;


    @Pointcut("execution(* com.adnanbk.ecommerce.services.imp.ProductServiceImp.*(..)) ")
    private void anyProductServiceMethod() {}
    @Pointcut("execution(* com.adnanbk.ecommerce.services.imp.UserServiceImpl.changeUserImage(..)) ")
    private void userChangeImage() {}
    @Pointcut("execution(* com.adnanbk.ecommerce.services.imp.AuthServiceImp.*(..)) ")
    private void anyUserServiceMethod() {}

    @AfterReturning(value = "userChangeImage()",returning = "image")
    public void afterUserChangeImage(ImageDto image) {
        image.setUrl(toImageUrl(image.getUrl()));
    }
    @AfterReturning(value = "anyUserServiceMethod()",returning = "user")
    public void afterUserMethods(AppUser user) {
        user.setImageUrl(toImageUrl(user.getImageUrl()));
    }
    @AfterReturning(value = "anyUserServiceMethod()",returning = "authData")
    public void afterUserMethods(AuthDataDto authData) {
        authData.getAppUser().setImageUrl(toImageUrl(authData.getAppUser().getImageUrl()));
    }
    @AfterReturning(value = "anyProductServiceMethod()",returning = "product")
    public void afterProductAspect(Product  product) {
        setProductImageUrl(product);
    }

    @AfterReturning(value = "anyProductServiceMethod()",returning = "products")
    public void afterProductsPageAspect(Page<Product>  products) {
        products.forEach(this::setProductImageUrl);
    }

    @AfterReturning(value = "anyProductServiceMethod()",returning = "products")
    public void afterProductsListAspect(List<Product>  products) {
        products.forEach(this::setProductImageUrl);
    }

    private void setProductImageUrl(Product product) {
        String imageName = product.getImage();
        if(StringUtils.hasLength(imageName))
          product.setImage((imageName.contains("luv2code") ? externalImagesPathUrl : imagesPathUrl)+imageName);
    }

    private String toImageUrl(String imageName) {
        return (StringUtils.hasLength(imageName) && !imageName.startsWith("http"))?(imagesPathUrl+imageName): imageName;
    }


    }
