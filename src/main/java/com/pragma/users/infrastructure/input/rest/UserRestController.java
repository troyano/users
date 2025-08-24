package com.pragma.users.infrastructure.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@Operation(summary = "Check if a user is an owner", description = "Returns true if the user with the given userName has the OWNER role.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "User ownership status returned", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
	})
	@GetMapping("/{userName}/is-owner")
	public ResponseEntity<Boolean> isOwner(@PathVariable String userName) {
		Boolean isOwner = userHandler.isOwner(userName);
		return ResponseEntity.ok(isOwner);
	}
}