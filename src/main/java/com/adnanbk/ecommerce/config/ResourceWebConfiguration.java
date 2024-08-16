package com.adnanbk.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + uploadDir + "/**").addResourceLocations("file:" + uploadDir + "/");
    }
}
