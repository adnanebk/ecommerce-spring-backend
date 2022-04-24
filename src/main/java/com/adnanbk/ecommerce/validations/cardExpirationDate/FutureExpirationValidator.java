package com.adnanbk.ecommerce.validations.cardExpirationDate;

import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;


public class FutureExpirationValidator implements ConstraintValidator<FutureExpirationDate, String> {


    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        if (Strings.isNullOrEmpty(dateStr)) {
            return false;
        }
        String[] monthYear = dateStr.split("/");
        if(monthYear.length!=2)
            return false;
        return LocalDate.of(2000+Integer.parseInt(monthYear[1]),
                          Integer.parseInt(monthYear[0])+1,
                      1).isAfter(LocalDate.now());
    }
}
