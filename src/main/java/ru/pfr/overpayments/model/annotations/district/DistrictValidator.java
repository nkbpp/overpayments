package ru.pfr.overpayments.model.annotations.district;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DistrictValidator implements ConstraintValidator<District, Integer> {

    @Override
    public boolean isValid(Integer indexDistrict, ConstraintValidatorContext context) {
        return  indexDistrict != null &&
                indexDistrict != 8 &&
                indexDistrict != 9 &&
                indexDistrict != 10 &&
                indexDistrict != 11 &&
                indexDistrict != 12;
    }
}
