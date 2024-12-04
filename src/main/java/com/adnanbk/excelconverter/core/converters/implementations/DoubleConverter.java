package com.adnanbk.excelconverter.core.converters.implementations;

import com.adnanbk.excelconverter.core.converters.Converter;


public class DoubleConverter implements Converter<Double> {

    @Override
    public Double convertToFieldValue(String cellValue) {
        return Double.parseDouble(cellValue.replace(",","."));
    }

    @Override
    public String convertToCellValue(Double fieldValue) {
        return fieldValue.toString();
    }
}
