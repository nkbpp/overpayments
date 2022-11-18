package ru.pfr.overpayments.model.annotations.district;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DistrictValidatorTest {

    private final Validator validator = Validation
            .buildDefaultValidatorFactory()
            .getValidator();

    private class TestObject {

        @District
        private Integer district;

        public TestObject() {
        }

        public void setDistrict(Integer district) {
            this.district = district;
        }

    }

    @Test
    public void whenAllAcceptable() {
        TestObject testObject = new TestObject();
        testObject.setDistrict(2);

        Set<ConstraintViolation<TestObject>> violations = validator.validate(testObject);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenAllNull() {
        TestObject testObject = new TestObject();
        testObject.setDistrict(null);

        Set<ConstraintViolation<TestObject>> violations = validator.validate(testObject);

        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getMessage().equals("District cannot be null"));
    }

    @Test
    public void whenDistrictLessThanZero() {
        TestObject testObject = new TestObject();
        testObject.setDistrict(-1);

        Set<ConstraintViolation<TestObject>> violations = validator.validate(testObject);

        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getMessage().equals("Value should be greater then equal to 0"));
    }

    @Test
    public void whenDistrictMoreThanTwentySeven() {
        TestObject testObject = new TestObject();
        testObject.setDistrict(28);

        Set<ConstraintViolation<TestObject>> violations = validator.validate(testObject);

        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getMessage().equals("Value should be less then equal to 27"));
    }

}