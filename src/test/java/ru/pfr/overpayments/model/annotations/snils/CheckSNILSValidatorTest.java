package ru.pfr.overpayments.model.annotations.snils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.THROWABLE;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Data
@AllArgsConstructor
class TestSNILSValidatorObject {
    @CheckSNILS
    private String snils;
}

class CheckSNILSValidatorTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    @Test
    public void whenAllAcceptable() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject("112-233-445 95");

        Set<ConstraintViolation<TestSNILSValidatorObject>> violations = validator.validate(testObject);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenAllNull() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject(null);

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            validator.validate(testObject);
        });

        assertThat(thrown).getCause().hasMessageContaining("SNILS is null");
        assertThat(thrown).extracting(Throwable::getCause, as(THROWABLE))
                .hasMessageContaining("SNILS is null");
    }

    @Test
    public void whenChecksumIsIncorrect() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject("112-233-446 95");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            validator.validate(testObject);
        });

        assertThat(thrown).getCause().hasMessageContaining("is wrong! Correct is");
        assertThat(thrown).extracting(Throwable::getCause, as(THROWABLE))
                .hasMessageContaining("is wrong! Correct is");
    }

    @Test
    public void whenLengthMore() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject("112-233-446 954");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            validator.validate(testObject);
        });

        assertThat(thrown).getCause().hasMessageContaining("does not match the mask ddd-ddd-ddd dd");
        assertThat(thrown).extracting(Throwable::getCause, as(THROWABLE))
                .hasMessageContaining("does not match the mask ddd-ddd-ddd dd");
    }

    @Test
    public void whenLengthLess() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject("112-233-446 9");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            validator.validate(testObject);
        });

        assertThat(thrown).getCause().hasMessageContaining("does not match the mask ddd-ddd-ddd dd");
        assertThat(thrown).extracting(Throwable::getCause, as(THROWABLE))
                .hasMessageContaining("does not match the mask ddd-ddd-ddd dd");
    }

    @Test
    public void whenIncorrectSymbol() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject("112-233-446 9w");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            validator.validate(testObject);
        });

        assertThat(thrown).getCause().hasMessageContaining("does not match the mask ddd-ddd-ddd dd");
        assertThat(thrown).extracting(Throwable::getCause, as(THROWABLE))
                .hasMessageContaining("does not match the mask ddd-ddd-ddd dd");
    }

/*    @Test
    public void whenLessThan1001998() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject("000-001-997 56");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            validator.validate(testObject);
        });

        assertThat(thrown).getCause().hasMessageContaining("is wrong! Correct is");
        assertThat(thrown).extracting(Throwable::getCause, as(THROWABLE))
                .hasMessageContaining("is wrong! Correct is");
    }*/

    @Test
    public void whenSumMoreThan101() {
        TestSNILSValidatorObject testObject = new TestSNILSValidatorObject("999-999-999 01");

        Set<ConstraintViolation<TestSNILSValidatorObject>> violations = validator.validate(testObject);

        assertThat(violations).isEmpty();
    }
    
}