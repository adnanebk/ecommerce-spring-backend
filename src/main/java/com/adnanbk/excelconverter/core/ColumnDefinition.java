package com.adnanbk.excelconverter.core;

import com.adnanbk.excelconverter.core.converters.CellConverter;
import com.adnanbk.excelconverter.core.converters.Converter;
import com.adnanbk.excelconverter.core.converters.EnumConverter;
import com.adnanbk.excelconverter.core.converters.FieldConverter;
import com.adnanbk.excelconverter.core.converters.implementations.CellConverterImp;
import com.adnanbk.excelconverter.core.converters.implementations.EnumConverterImp;
import com.adnanbk.excelconverter.core.converters.implementations.FieldConverterImp;
import com.adnanbk.excelconverter.core.converters.implementations.TwoWaysConvertersImp;


public class ColumnDefinition<T> {

    private Class<T> classType;
    private int columnIndex;
    private String fieldName;
    private String title;
    private Converter<T> converter;

    private ColumnDefinition(int columnIndex, String fieldName, String title, Converter<T> converter,Class<T> classType) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        this.title = title;
        this.converter = converter;
        this.classType = classType;
    }

    public static <T> ColumnDefinition<T> with(int columnIndex, String fieldName, String title) {
        return new ColumnDefinition<>(columnIndex,fieldName,title,null,null);

    }
    public static <T> ColumnDefinition<T> withConverter(int columnIndex, String fieldName, String title, Class<T> fieldType, Converter<T> converter) {
        return new ColumnDefinition<>(columnIndex,fieldName,title,converter,fieldType);
    }
    public static <T> ColumnDefinition<T> withConverters(int columnIndex, String fieldName, String title, Class<T> fieldType, CellConverter<T> cellConverter,FieldConverter<T> fieldConverter) {
        return new ColumnDefinition<>(columnIndex,fieldName,title,new TwoWaysConvertersImp<>(fieldConverter,cellConverter),fieldType);
    }

    public static <T> ColumnDefinition<T> withCellConverter(int columnIndex, String fieldName, String title, Class<T> fieldType, CellConverter<T> cellConverter) {
        return new ColumnDefinition<>(columnIndex,fieldName,title,new CellConverterImp<>(cellConverter),fieldType);
    }

    public static <T> ColumnDefinition<T> withFieldConverter(int columnIndex, String fieldName, String title, Class<T> fieldType, FieldConverter<T> fieldConverter) {
        return new ColumnDefinition<>(columnIndex,fieldName,title,new FieldConverterImp<>(fieldConverter),fieldType);
    }

    public static <T extends Enum<T>> ColumnDefinition<T> withEnumConverter(int columnIndex, String fieldName, String title, Class<T> fieldType, EnumConverter<T> converter) {
        return new ColumnDefinition<>(columnIndex,fieldName,title,new EnumConverterImp<>(fieldType,converter),fieldType);
    }

    public int getColumnIndex() {
        return columnIndex;
    }


    public String getFieldName() {
        return fieldName;
    }

    public String getTitle() {
        return title;
    }

    public Converter<T> getConverter() {
        return converter;
    }

    public void setConverter(Converter<?> converter,Class<?> classType) {
        this.classType = (Class<T>) classType;
        this.converter = (Converter<T>) converter;
    }

    public Class<T> getClassType() {
        return classType;
    }
}
