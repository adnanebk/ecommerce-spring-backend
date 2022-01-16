package com.adnanbk.ecommerce.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUserValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface UniqueEmail {


    String message() default "Already exists";

    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};
}
