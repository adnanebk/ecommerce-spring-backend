package com.adnanbk.ecommerce.config;

import com.adnanbk.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ExposeEntitiesRestConfiguration implements RepositoryRestConfigurer, WebMvcConfigurer {


    @Value("${front.url}")
    private String origin;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        cors.addMapping("/api/**").allowedOrigins(origin);
        config.exposeIdsFor(ProductCategory.class, Product.class, AppUser.class, CreditCard.class, UserOrder.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry cors) {
        cors.addMapping("/api/**").allowedOrigins(origin);
    }
}

