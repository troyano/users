package com.pragma.users.domain.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String email;
	@NotBlank
	private String password;
}
