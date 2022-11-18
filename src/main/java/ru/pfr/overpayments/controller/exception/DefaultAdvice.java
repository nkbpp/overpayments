package ru.pfr.overpayments.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.pfr.overpayments.model.annotations.snils.SNILSValidatorExeption;
import ru.pfr.overpayments.model.dto.AppError;

//https://habr.com/ru/post/528116/
//@ControllerAdvice(annotations = CustomExceptionHandler.class)
@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(SNILSValidatorExeption.class)
    public ResponseEntity<?> handleSNILSValidatorExeption(SNILSValidatorExeption e) {
        return new ResponseEntity<>(
                new AppError(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()
                ),
                HttpStatus.BAD_REQUEST);
    }

}
