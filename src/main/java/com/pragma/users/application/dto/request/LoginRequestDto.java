package com.pragma.users.application.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String email;
	@NotBlank
	private String password;
}
