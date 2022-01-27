package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError {

    private String fieldName;
    private String formattedName;
    private String message;


    @Override
    public String toString() {
        return formattedName + ' ' + message;
    }

    public ResponseError(String fieldName, String formattedName, String message) {
        this.fieldName = fieldName;
        this.formattedName = formattedName;
        this.message = message;
    }

    public ResponseError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }


}
