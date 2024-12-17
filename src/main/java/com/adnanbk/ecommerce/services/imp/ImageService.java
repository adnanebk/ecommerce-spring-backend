package com.adnanbk.ecommerce.services.imp;


import com.adnanbk.ecommerce.exceptions.CustomFileException;
import com.adnanbk.ecommerce.services.FileService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.FileNameUtils;
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
    private static final List<String> SUPPORTED_EXTENSIONS = List.of("jpg","jpeg","png","webp");
    private static final double MAX_SIZE_MB = 3;
    public ImageService(FileService fileService) {
        this.fileService = fileService;
    }

    public String upload(MultipartFile multipartFile) {
        validateImage(multipartFile);
        return fileService.upload(multipartFile);
    }

    public List<String> upload(List<MultipartFile> images) {
        images.forEach(this::validateImage);
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

    private  void validateImage(MultipartFile multipartFile) {
        String extension = FileNameUtils.getExtension(multipartFile.getOriginalFilename());
        var sizeInMb = multipartFile.getSize()/(1000*1000);
        if(sizeInMb>MAX_SIZE_MB)
            throw new CustomFileException("File is to large, maximum size is "+MAX_SIZE_MB+".xxMB");
        if(!SUPPORTED_EXTENSIONS.contains(extension))
            throw new CustomFileException("Invalid file type, we support only "+ String.join(", ", SUPPORTED_EXTENSIONS));
    }

}
