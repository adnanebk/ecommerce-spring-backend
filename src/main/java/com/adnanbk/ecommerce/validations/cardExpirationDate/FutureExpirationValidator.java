package com.adnanbk.ecommerce.validations.cardExpirationDate;

import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


public class FutureExpirationValidator implements ConstraintValidator<FutureExpirationDate, String> {


    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        if (Strings.isNullOrEmpty(dateStr)) {
            return false;
        }
        return  YearMonth.parse(dateStr,DateTimeFormatter.ofPattern("MM/yy")).isAfter(YearMonth.now());

    }
}
