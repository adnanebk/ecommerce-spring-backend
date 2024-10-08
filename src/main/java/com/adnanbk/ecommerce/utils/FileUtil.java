package com.adnanbk.ecommerce.utils;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "uploads")
@Getter
@Setter
public class FileUtil {
    private String imagesPathUrl;
    private String externalImagesPathUrl;


    public String toImageUrl(String imageName) {
        String url = "";
        if (StringUtils.hasLength(imageName) && !imageName.startsWith("http"))
            url = imageName.contains("luv2code") ? externalImagesPathUrl : imagesPathUrl;
        return url + imageName;
    }

    public List<String> toImagesUrlS(String imageName) {
        return Arrays.stream(imageName.split(Constants.IMAGES_SEPARATOR)).map(this::toImageUrl).toList();
    }


}
