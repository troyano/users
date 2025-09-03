package com.pragma.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserClient {

	private String firstName;
	private String lastName;
	private String identityDocument;
	private String phone;
	private String email;
	private String password;
	private Long roleId;
}
