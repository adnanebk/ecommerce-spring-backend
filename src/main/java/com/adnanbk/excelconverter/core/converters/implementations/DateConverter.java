package com.adnanbk.excelconverter.core.converters.implementations;

import com.adnanbk.excelconverter.core.converters.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;


public class DateConverter implements Converter<Date> {

    private static final ThreadLocal<SimpleDateFormat> FORMATTER = ThreadLocal.withInitial(SimpleDateFormat::new);


    @Override
    public  Date convertToFieldValue(String cellValue) {
        try {
            return FORMATTER.get().parse(cellValue);
        } catch (ParseException e) {
            throw new DateTimeException("failed to parse to date "+cellValue);
        } finally {
            FORMATTER.remove();
        }
    }

    @Override
    public String convertToCellValue(Date fieldValue) {
        try {
            return FORMATTER.get().format(fieldValue);
        }
        finally {
            FORMATTER.remove();
        }
    }
}
