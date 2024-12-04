package com.adnanbk.excelconverter.core.reflection;


import com.adnanbk.excelconverter.core.ColumnDefinition;
import com.adnanbk.excelconverter.core.converters.Converter;
import com.adnanbk.excelconverter.core.converters.implementations.*;
import com.adnanbk.excelconverter.exceptions.ReflectionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class ReflectionHelper<T> {
    private final List<ReflectedField<?>> fields = new ArrayList<>();
    private final List<String> headers = new ArrayList<>();
    private final Class<T> classType;
    private final Constructor<T> defaultConstructor;

    public ReflectionHelper(Class<T> type) {
        classType = type;
        defaultConstructor = getDefaultConstructor();}

    public ReflectionHelper(Class<T> type, ColumnDefinition<?>[] columnsDefinitions) {
        classType = type;
        defaultConstructor = getDefaultConstructor();
        Arrays.sort(columnsDefinitions,Comparator.comparing(ColumnDefinition::getColumnIndex));
        for (var cd : columnsDefinitions) {
            try {
                Field field = type.getDeclaredField(cd.getFieldName());
                if (cd.getConverter() == null)
                    cd.setConverter(this.createDefaultConverter(field),field.getType());
                if(!cd.getClassType().equals(field.getType()))
                    throw new ReflectionException(String.format("The converter of the field %s is not compatible with its type %s",cd.getFieldName(),field.getType().getSimpleName()));
                fields.add(new ReflectedField<>(field, cd.getConverter(), cd.getColumnIndex()));
                headers.add(cd.getTitle());
            } catch (NoSuchFieldException e) {
                throw new ReflectionException("the specified field name '" + cd.getFieldName() + "' is not found in the class '"+classType.getSimpleName()+"'");
            }
        }
    }

    public List<ReflectedField<?>> getFields() {
        return fields;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public T createInstance() {
        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionException(e.getMessage());
        }
    }

    private Constructor<T> getDefaultConstructor() {
        try {
            return classType.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("No default constructor found");
        }
    }

    private Converter<?> createDefaultConverter(Field field) {
        if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class))
            return new BooleanConverterImp();
        if (field.getType().isEnum())
            return new EnumConverterImp<>(field.getType(), new HashMap<>());

        if (field.getType().equals(Date.class)) {
            return new DateConverter();
        }
        if (field.getType().equals(LocalDate.class)) {
            return new LocalDateConverter();
        }
        if (field.getType().equals(LocalDateTime.class)) {
            return new LocalDateTimeConverter();
        }
        if (field.getType().equals(ZonedDateTime.class)) {
            return new ZonedDateConverter();
        }
        if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            return new IntegerConverter();
        }
        if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
            return new LongConverter();
        }
        if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
            return new DoubleConverter();
        }
        return null;
    }


}

