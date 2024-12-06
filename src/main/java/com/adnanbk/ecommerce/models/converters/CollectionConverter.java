package com.adnanbk.ecommerce.models.converters;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

public class CollectionConverter implements AttributeConverter<List<String>,String> {

    public static final String SEPARATOR = ";;";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(SEPARATOR,attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(SEPARATOR)).toList();
    }
}
