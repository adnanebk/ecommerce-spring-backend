package com.adnanbk.ecommerceang.validations;

import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
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

        if(userRepo==null)
            return true;

      if(appUser==null)
        return false;

        boolean isValid = appUser.getConfirmPassword().equals(appUser.getPassword());

        return isValid;
    }
}
