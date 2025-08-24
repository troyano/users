package com.pragma.users.application.dto.request;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@Pattern(regexp = "\\d+", message = "The document must be numeric")
	private String identityDocument;

	@NotBlank
	@Size(max = 13, message = "The cell phone cannot exceed 13 characters")
	@Pattern(regexp = "^\\+?[0-9]*$", message = "The cell phone can only contain numbers and optionally the + symbol")
	private String phone;

	@NotNull
	private LocalDate birthDate;

	@NotBlank
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank
	private String password;
}
