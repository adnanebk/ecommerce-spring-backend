package com.adnanbk.excelconverter.core.converters.implementations;

import com.adnanbk.excelconverter.core.converters.Converter;
import com.adnanbk.excelconverter.exceptions.ConverterException;

public class BooleanConverterImp implements Converter<Boolean> {
    private static final  String TRUE_VALUE = "true";
    private static final String  FALSE_VALUE = "false";


    @Override
    public String convertToCellValue(Boolean fieldValue) {
        return  (fieldValue != null && fieldValue) ? TRUE_VALUE : FALSE_VALUE;
    }

    @Override
    public Boolean convertToFieldValue(String cellValue) {
        if(TRUE_VALUE.equalsIgnoreCase(cellValue))
            return true;
        else if(FALSE_VALUE.equalsIgnoreCase(cellValue))
            return false;
        throw new ConverterException("Cannot convert to boolean, you must create a converter");
    }
}
