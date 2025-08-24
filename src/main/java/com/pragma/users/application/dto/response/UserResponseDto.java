package com.pragma.users.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
	private Long id;
	private String nombre;
	private String apellido;
	private String correo;
	private String role;
}
