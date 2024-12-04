package com.adnanbk.excelconverter.core.converters.implementations;

import com.adnanbk.excelconverter.core.converters.Converter;
import com.adnanbk.excelconverter.core.converters.EnumConverter;
import com.adnanbk.excelconverter.exceptions.ConverterException;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class EnumConverterImp<T extends Enum<T>> implements Converter<T> {
    private final EnumMap<T,String> enumToCellValue;
    private final  Map<String,T> cellValueToEnum;

    public EnumConverterImp(Class<?> type, Map<?,String> map) {
        if(!type.isEnum())
            throw new ConverterException("Can't convert " + type + " to Enum");
        Class<T> enumType = (Class<T>) type;
       enumToCellValue = new EnumMap<>(enumType);
       cellValueToEnum = new HashMap<>();
        for(var entry : map.entrySet()){
            enumToCellValue.put(enumType.cast(entry.getKey()),entry.getValue());
            cellValueToEnum.put(entry.getValue().toUpperCase(), enumType.cast(entry.getKey()));
        }
        for (var constant : enumType.getEnumConstants()) {
            enumToCellValue.putIfAbsent(constant, constant.toString());
            cellValueToEnum.putIfAbsent(constant.toString(), constant);
        }
    }

    public EnumConverterImp(Class<?> type, EnumConverter<?> enumConverter) {
        this(type,enumConverter.convert());
    }


    @Override
    public String convertToCellValue(T fieldValue) {
        return enumToCellValue.getOrDefault(fieldValue,"");
    }

    @Override
    public T convertToFieldValue(String cellValue) {
        return cellValueToEnum.get(cellValue.toUpperCase());
    }
}
