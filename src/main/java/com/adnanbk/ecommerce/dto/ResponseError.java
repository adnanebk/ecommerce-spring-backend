package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError {

    private String fieldName;
    private String message;



    public ResponseError(String fieldName , String message) {
        this.fieldName = fieldName;
        this.message =  message;
    }



}
