package com.pragma.users.domain.exception;

import com.pragma.users.domain.util.Constants;

public class ValidationUtils {

	public static void validateEmail(String email) {
		if (!email.matches(Constants.REGEX_EMAIL)) {
			throw new ValidationException(Constants.MSG_INVALID_EMAIL_FORMAT);
		}
	}

	public static void validatePhone(String phone) {
		if (!phone.matches(Constants.REGEX_CELL_PHONE)) {
			throw new ValidationException(Constants.MSG_CELL_PHONE_REGEX);
		}
	}

	public static void validateIdentityDocument(String identityDocument) {
		if (!identityDocument.matches(Constants.REGEX_ID)) {
			throw new ValidationException(Constants.MSG_ID_NUMERIC);
		}
	}

	public static void validateAdultAge(int age) {
		if (age < Constants.ADULT_AGE) {
			throw new ValidationException(Constants.MSG_OF_LEGAL_AGE);
		}
	}

	private ValidationUtils() {
		super();
	}
}
