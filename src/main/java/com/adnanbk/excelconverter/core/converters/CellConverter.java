package com.adnanbk.excelconverter.core.converters;

public interface CellConverter<T>  {

    T convertToFieldValue(String cellValue);

}
