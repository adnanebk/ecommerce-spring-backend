package com.adnanbk.excelconverter.core.converters.implementations;

import com.adnanbk.excelconverter.core.converters.CellConverter;
import com.adnanbk.excelconverter.core.converters.Converter;
import com.adnanbk.excelconverter.core.converters.FieldConverter;

public class TwoWaysConvertersImp<T>  implements Converter<T> {

    private final FieldConverter<T> fieldConverter;
    private final CellConverter<T> cellConverter;

    public TwoWaysConvertersImp(FieldConverter<T> fieldConverter, CellConverter<T> cellConverter) {
        this.fieldConverter = fieldConverter;
        this.cellConverter = cellConverter;
    }

    @Override
    public T convertToFieldValue(String cellValue) {
        return cellConverter.convertToFieldValue(cellValue);
    }

    @Override
    public String convertToCellValue(T fieldValue) {
        return fieldConverter.convertToCellValue(fieldValue);
    }
}
