package com.adnanbk.excelconverter.core.converters;

public interface FieldConverter<T>{

    String convertToCellValue(T fieldValue);

}
