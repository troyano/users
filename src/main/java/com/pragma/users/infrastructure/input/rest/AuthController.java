package com.pragma.users.infrastructure.input.rest;

import java.util.Map;

import javax.validation.Valid;

import com.pragma.users.infrastructure.configuration.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.users.application.dto.request.LoginRequestDto;
import com.pragma.users.application.dto.response.TokenResponseDto;
import com.pragma.users.application.handler.IUserHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final JwtService jwtService;
	private final IUserHandler userHandler;

	@PostMapping("/login")
	public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto req) {
		return ResponseEntity.ok(userHandler.auth(req));
	}

	@GetMapping("/jwks.json")
	public Map<String, Object> jwks() {
		return jwtService.jwkSetPublic().toJSONObject();
	}
}
