package com.adnanbk.ecommerceang.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ResponseError {

    private String fieldName;
    private String name;
    private String message;

    @Override
    public String toString() {
        return  name + ' '+message;
    }

    public ResponseError(String fieldName, String message) {
        this.name=fieldName;
        if(!fieldName.equals(fieldName.toLowerCase()))
            this.name= normalizejsonName(fieldName);
        this.fieldName =  StringUtils.uncapitalize(fieldName);
        this.message = message;
    }

    private String normalizejsonName(String fieldName) {
        StringBuilder name= new StringBuilder();
        for (char c : fieldName.toCharArray()) {
            if(Character.isUpperCase(c))
                name.append(" ");
            name.append(c);
        }
        return name.toString().toLowerCase();
    }
}
