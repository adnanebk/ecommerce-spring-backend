package com.adnanbk.excelconverter.core.converters;

import java.util.Map;

public interface EnumConverter<T extends Enum<?>> {
   Map<T,String> convert();

    }
