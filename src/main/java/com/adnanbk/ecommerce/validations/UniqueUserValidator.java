package com.adnanbk.ecommerce.validations;

import com.adnanbk.ecommerce.reposetories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueUserValidator implements ConstraintValidator<UniqueEmail, String> {


    private UserRepo userRepo;


    @Autowired
    public UniqueUserValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UniqueUserValidator() {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        if (userRepo == null)
            return true;

        boolean isValid = !userRepo.existsByEmail(s);
        return isValid;
    }
}
