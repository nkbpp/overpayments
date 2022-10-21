package ru.pfr.overpayments.model.annotations.district;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull(message = "District cannot be null")
@Min(value = 0 , message = "Value should be greater then equal to 0")
@Max(value = 27 , message = "Value should be less then equal to 27")
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DistrictValidator.class)
public @interface District {
    String message() default "There is no area with this code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

