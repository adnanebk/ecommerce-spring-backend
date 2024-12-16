package com.adnanbk.ecommerce.services.imp;


import com.adnanbk.ecommerce.services.FileService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "uploads")
@Getter
@Setter
public class ImageService implements FileService {

    private final FileService fileService;

    private String imagesPathUrl;
    private String externalImagesPathUrl;

    public ImageService(FileService fileService) {
        this.fileService = fileService;
    }

    public String upload(MultipartFile multipartFile) {
        return fileService.upload(multipartFile);
    }

    public List<String> upload(List<MultipartFile> images) {
        return fileService.upload(images);
    }
    public String toUrl(String imageName) {
        if(!StringUtils.hasLength(imageName))
            return "";
        String url = "";
        if (StringUtils.hasLength(imageName) && !imageName.startsWith("http"))
            url = imageName.contains("luv2code") ? externalImagesPathUrl : imagesPathUrl;
        return url + imageName;
    }

    public List<String> toUrls(List<String> imageNames) {
        if(imageNames==null || imageNames.isEmpty())
            return new ArrayList<>();
        return imageNames.stream().map(this::toUrl).toList();
    }


    public String toImageName( String imageUrl) {
       return imageUrl.substring(imageUrl.indexOf('/')+1);
    }

}
