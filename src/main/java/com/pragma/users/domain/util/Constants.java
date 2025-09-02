package com.pragma.users.domain.util;

public class Constants {
	public static final String ROLE_OWNER = "OWN";
	public static final String ROLE_ADMIN = "ADM";
	public static final String ROLE_EMPLOYEE = "EMP";
	// Messages
	public static final String MSG_ROLE_OWNER_DOES_NOT_EXIST = "Role OWNER does not exist";
	public static final String MSG_ROLE_EMPLOYEE_DOES_NOT_EXIST = "Role EMPLOYEE does not exist";
	public static final String MSG_OF_LEGAL_AGE = "The user must be of legal age";
	public static final String MSG_CELL_PHONE_MAX_LENGTH = "The cell phone cannot exceed 13 characters";
	public static final String MSG_CELL_PHONE_REGEX = "The cell phone can only contain numbers and optionally the + symbol";
	public static final String MSG_ID_NUMERIC = "The document must be numeric";
	public static final String MSG_INVALID_EMAIL_FORMAT = "Invalid email format";
	public static final String MSG_INVALID_CREDENTIALS  = "Credenciales inválidas";

	public static final String MSG_USER_EMPLOYEE_ALREADY_EXISTS = "Employee already exists";
	// Regular Expressions
	public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
	public static final String REGEX_CELL_PHONE = "^[+]?[0-9]{1,13}$";
	public static final String REGEX_ID = "^[0-9]+$";
	// Magic Numbers
	public static final int ADULT_AGE = 18;

	public static final int NUMBER_13 = 13;
	public static final int NUMBER_18 = 18;
	public static final int NUMBER_15 = 15;
	public static final int NUMBER_1990 = 1990;
	public static final int NUMBER_60 = 60;
	
	public static final long TOKEN_TIME = 0;

	private Constants() {
	}
}