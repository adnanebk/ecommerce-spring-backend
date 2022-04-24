/*
package com.adnanbk.ecommerce.validations;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.models.UserOrder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class OrderValidator implements Validator {


    private final javax.validation.Validator validator;

    public OrderValidator(javax.validation.Validator validator) {
        this.validator = validator;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return UserOrder.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserOrder userOrder = (UserOrder) o;
        CreditCard creditCard = userOrder.getCreditCard();
        var cardErrors = validator.validate(creditCard);
        if (!cardErrors.isEmpty())
            throw new ConstraintViolationException(cardErrors);
        int year = 2000 + Integer.parseInt(creditCard.getExpirationDate().split("/")[1]);
        int month = Integer.parseInt(creditCard.getExpirationDate().split("/")[0]);
        LocalDate expirationDate = LocalDate.of(year, month, 1);
        if (expirationDate.isBefore(LocalDate.now()))
            errors.reject("expirationDate", "has passed");
    }

}

*/
