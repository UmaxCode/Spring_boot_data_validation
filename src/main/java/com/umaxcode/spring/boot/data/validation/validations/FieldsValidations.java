package com.umaxcode.spring.boot.data.validation.validations;

import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FieldsValidations<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public void validate(T objectToValidate){

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(objectToValidate);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }
    }


}
