package com.adnanbk.excelconverter.core.converters.implementations;

import com.adnanbk.excelconverter.core.converters.Converter;


public class IntegerConverter implements Converter<Integer> {

    @Override
    public Integer convertToFieldValue(String cellValue) {
        return Integer.parseInt(cellValue);
    }

    @Override
    public String convertToCellValue(Integer fieldValue) {
        return fieldValue.toString();
    }
}
