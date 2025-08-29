package com.pragma.users.application.dto.request;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.pragma.users.domain.util.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerRequestDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@Pattern(regexp = Constants.REGEX_ID, message = Constants.MSG_ID_NUMERIC)
	private String identityDocument;

	@NotBlank
	@Size(max = Constants.NUMBER_13, message = Constants.MSG_CELL_PHONE_MAX_LENGTH)
	@Pattern(regexp = Constants.REGEX_CELL_PHONE, message = Constants.MSG_CELL_PHONE_REGEX)
	private String phone;

	@NotNull
	private LocalDate birthDate;

	@NotBlank
	@Email(message = Constants.MSG_INVALID_EMAIL_FORMAT)
	private String email;

	@NotBlank
	private String password;
}