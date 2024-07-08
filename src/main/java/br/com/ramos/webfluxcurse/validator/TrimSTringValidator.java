package br.com.ramos.webfluxcurse.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimSTringValidator implements ConstraintValidator<TrimgString,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() <= 0) {
            return true;
        }
        return s.trim().length() ==s.length();
    }
}
