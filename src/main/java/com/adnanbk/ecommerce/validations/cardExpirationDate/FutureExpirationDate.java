package com.adnanbk.ecommerce.validations.cardExpirationDate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureExpirationValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface FutureExpirationDate {


    String message() default "{error.future}";

    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};
}
