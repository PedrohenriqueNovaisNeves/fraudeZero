package com.example.fraudeZero.service.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingCepAndAddressValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingCepAndAddress {

    String message() default "The zip code does not exist or the street does not match the address.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
