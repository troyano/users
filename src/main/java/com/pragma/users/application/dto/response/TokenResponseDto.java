package com.pragma.users.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponseDto {

	private String accessToken;
	private long expiresInSeconds;
}
