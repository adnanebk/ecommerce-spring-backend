package com.adnanbk.ecommerce.validations.confirmPassword;

import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, AppUser> {

    private UserRepo userRepo;

    @Autowired
    public ConfirmPasswordValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ConfirmPasswordValidator() {
    }

    @Override
    public boolean isValid(AppUser appUser, ConstraintValidatorContext constraintValidatorContext) {

        if (userRepo == null)
            return true;

        if (appUser == null)
            return false;

        return true;
        //return appUser.getConfirmPassword().equals(appUser.getPassword());
    }
}
