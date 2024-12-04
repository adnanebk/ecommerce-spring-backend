package com.adnanbk.excelconverter.core.converters.implementations;

import com.adnanbk.excelconverter.core.converters.Converter;


public class LongConverter implements Converter<Long> {

    @Override
    public Long convertToFieldValue(String cellValue) {
        return Long.parseLong(cellValue);
    }

    @Override
    public String convertToCellValue(Long fieldValue) {
        return fieldValue.toString();
    }
}
