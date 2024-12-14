package dev.gmarchev.flightmanager.validation.phonenumber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ValidNotEmptyPhoneNumberValidator.class) // Specify the validator class
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNotEmptyPhoneNumber {

	String message() default "Phone number must contain only numbers, spaces and optional leading '+'";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
