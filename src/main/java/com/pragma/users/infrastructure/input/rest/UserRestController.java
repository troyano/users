package com.pragma.users.infrastructure.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.users.application.dto.request.OwnerRequestDto;
import com.pragma.users.application.handler.IUserHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

	private final IUserHandler userHandler;

	@Operation(summary = "Create a new owner")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Owner created", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
			@ApiResponse(responseCode = "409", description = "User already exists", content = @Content) })
	@PostMapping("/owners")
	public ResponseEntity<Void> createOwner(final @RequestBody OwnerRequestDto ownerRequestDto) {
		userHandler.createOwner(ownerRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}