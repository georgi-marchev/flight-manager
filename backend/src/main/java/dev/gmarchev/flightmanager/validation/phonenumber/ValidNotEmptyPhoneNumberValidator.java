package dev.gmarchev.flightmanager.validation.phonenumber;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidNotEmptyPhoneNumberValidator implements ConstraintValidator<ValidNotEmptyPhoneNumber, String> {

	private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9\\s]*$");

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {

		if (phoneNumber == null || phoneNumber.isEmpty()) {

			return false;
		}

		return PHONE_PATTERN.matcher(phoneNumber)
				.matches();
	}
}
