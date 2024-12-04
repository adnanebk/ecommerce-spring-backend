package com.adnanbk.excelconverter.core.reflection;

import com.adnanbk.excelconverter.core.converters.Converter;
import com.adnanbk.excelconverter.exceptions.ReflectionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectedField<T> {
    private final Method getter;
    private final Method setter;
    private final int cellIndex;
    private final Converter<T> converter;
    private final String typeName;
    private final String name;

    public ReflectedField(Field field, Converter<T> converter, int cellIndex) {
        try {
            this.converter = converter;
            this.name = field.getName();
            this.getter = field.getDeclaringClass().getDeclaredMethod((field.getType().equals(boolean.class) ? "is" : "get") + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1));
            this.setter = field.getDeclaringClass().getDeclaredMethod("set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1), field.getType());
            this.cellIndex = cellIndex;
            typeName = getTypeName(field);
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("not found all getters and setters");
        }
    }

    public Object getValue(Object obj) {
        try {
            Object fieldValue = getter.invoke(obj);
            if (converter != null)
                return converter.convertToCellValue((T) fieldValue);
            return fieldValue;
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
            throw new ReflectionException(e.getMessage());
        }
    }

    public void setValue(Object cellValue, Object obj) {
        try {
            if (converter != null && cellValue instanceof String)
                setter.invoke(obj, converter.convertToFieldValue(cellValue.toString()));
            else setter.invoke(obj, cellValue);
        } catch (Exception e) {
            throw new ReflectionException(e.getMessage());
        }
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getName() {
        return name;
    }
    private String getTypeName(Field field) {
        if (field.getType().isEnum())
            return  "enum";
        else if (isNumericType(field.getType()))
            return  "number";
        return field.getType().getSimpleName().toLowerCase();
    }
    private boolean isNumericType(Class<?> clazz) {
        return clazz == byte.class || clazz == short.class || clazz == int.class ||
                clazz == long.class || clazz == float.class || clazz == double.class ||
                clazz == Byte.class || clazz == Short.class || clazz == Integer.class ||
                clazz == Long.class || clazz == Float.class || clazz == Double.class;
    }


}
