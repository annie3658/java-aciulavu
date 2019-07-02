package com.library.application.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<Rating, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(value >= 1d && value <= 5d){
            return true;
        }
        return false;
    }
}
