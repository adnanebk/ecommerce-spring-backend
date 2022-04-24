package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class ImageDto {

    private  String url;

    public ImageDto(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }


}
