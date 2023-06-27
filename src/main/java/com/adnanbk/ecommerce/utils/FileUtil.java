package com.adnanbk.ecommerce.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FileUtil {
    private static String imagesPathUrl;
    private static String externalImagesPathUrl;



    @Value("${uploads.imagesPathUrl}")
    private synchronized void setImagesPathUrl(String imagesPathUrl){
        FileUtil.imagesPathUrl=imagesPathUrl;
    }
    @Value("${uploads.externalImagesPathUrl}")
    private synchronized void  setExternalImagesPathUrl(String externalImagesPathUrl){
        FileUtil.externalImagesPathUrl=externalImagesPathUrl;
    }

    public  static String toImageUrl(String imageName) {
        String url = "";
        if(StringUtils.hasLength(imageName) && !imageName.startsWith("http"))
            url=imageName.contains("luv2code") ?externalImagesPathUrl:imagesPathUrl;
        return url+imageName;
    }


}
