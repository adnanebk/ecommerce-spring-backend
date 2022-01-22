package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

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

    public ResponseError(String fieldName, String message) {
        this.formattedName = fieldName;
        if (!fieldName.equals(fieldName.toLowerCase()))
            this.formattedName = formatToWordsWithSpaces(fieldName);
        this.fieldName = StringUtils.uncapitalize(fieldName);
        this.message = message;
    }

    private String formatToWordsWithSpaces(String fieldName) {
        return fieldName.replaceAll("([a-z])([A-Z]+)", "$1 $2").toLowerCase();
    }
}
