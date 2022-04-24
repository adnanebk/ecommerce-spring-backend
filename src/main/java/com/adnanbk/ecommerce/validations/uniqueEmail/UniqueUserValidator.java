package com.adnanbk.ecommerce.validations.uniqueEmail;

import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.google.api.client.util.Strings;
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
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (Strings.isNullOrEmpty(email)) {
            return false;
        }
        if (userRepo == null)
            return true;

        return userRepo.findByEmail(email).isEmpty();
    }
}
