package dev.gmarchev.flightmanager.validation.pin;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidNotEmptyEgnOrForeignerPinValidator implements
		ConstraintValidator<ValidNotEmptyEgnOrForeignerPin, String> {

	// EGN must be exactly 10 digits
	private static final Pattern EGN_PATTERN = Pattern.compile("^[0-9]{10}$");

	// Foreign Personal Number: Starts with 'P' and followed by 7 digits
	private static final Pattern FOREIGNER_PIN_PATTERN = Pattern.compile("^P\\d{7}$");


	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null || value.isEmpty()) {

			return false;
		}

		// TODO: EGN validation can be improved by checking all the requirements
		return EGN_PATTERN.matcher(value).matches() || FOREIGNER_PIN_PATTERN.matcher(value).matches();
	}
}
