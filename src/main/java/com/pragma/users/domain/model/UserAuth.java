package com.pragma.users.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

	private Long id;
	private String firstName;
	private String lastName;
	private String identityDocument;
	private String phone;
	private LocalDate birthDate;
	private String email;
	private String password;
	private String role;
}
