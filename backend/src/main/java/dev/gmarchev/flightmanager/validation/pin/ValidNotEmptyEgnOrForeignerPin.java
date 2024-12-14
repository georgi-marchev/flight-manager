package dev.gmarchev.flightmanager.validation.pin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Constraint(validatedBy = ValidNotEmptyEgnOrForeignerPinValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNotEmptyEgnOrForeignerPin {

	// Default error message
	String message() default "Invalid EGN or Foreigner Personal Number";

	// Groups for validation (optional)
	Class<?>[] groups() default {};

	// Payload for custom metadata (optional)
	Class<? extends Payload>[] payload() default {};
}
