package com.adnanbk.ecommerce.utils;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "uploads")
@Getter
@Setter
public class ImageUtil {
    private String imagesPathUrl;
    private String externalImagesPathUrl;


    public String toImageUrl(String imageName) {
        if(!StringUtils.hasLength(imageName))
            return "";
        String url = "";
        if (StringUtils.hasLength(imageName) && !imageName.startsWith("http"))
            url = imageName.contains("luv2code") ? externalImagesPathUrl : imagesPathUrl;
        return url + imageName;
    }

    public List<String> toImagesUrls(List<String> imageNames) {
        if(imageNames==null || imageNames.isEmpty())
            return new ArrayList<>();
        return imageNames.stream().map(this::toImageUrl).toList();
    }


    public String toImageName( String imageUrl) {
       return imageUrl.replace(imagesPathUrl,"").replace(externalImagesPathUrl,"");
    }
}
