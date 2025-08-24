package com.pragma.users.domain.util;

public class Constants {
	public static final String ROLE_OWNER = "OWN";

	public static final String MSG_ROLE_OWNER_DOES_NOT_EXIST = "Role OWNER does not exist";
	public static final String MSG_INVALID_EMAIL = "Invalid email";
	public static final String MSG_INVALID_CELL_PHONE = "Invalid cell phone";
	public static final String MSG_INVALID_ID = "Invalid ID";
	public static final String MSG_OF_LEGAL_AGE = "The user must be of legal age";

	public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
	public static final String REGEX_CELL_PHONE = "^[+]?[0-9]{1,13}$";
	public static final String REGEX_ID = "^[0-9]+$";
	// Magic Numbers
	public static final int NUMERO_18 = 18;
	public static final int NUMERO_15 = 15;
	public static final int NUMERO_1990 = 0;

	private Constants() {
	}
}
