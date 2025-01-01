package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.AuthenticationRequest;
import dev.gmarchev.flightmanager.dto.RefreshTokenRequest;
import dev.gmarchev.flightmanager.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {

		try {

			return ResponseEntity.ok(
					authenticationService.getAuthenticationResponse(authenticationRequest));

		} catch (Exception e) {

			log.error("Could not authenticate user.", e);

			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid credentials");
		}
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {

		try	{

			return ResponseEntity.ok(authenticationService.getAuthenticationResponse(refreshTokenRequest));

		} catch (ExpiredJwtException e) {

			log.error("Expired token.", e);

			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Token expired");
		} catch (Exception e) {

			log.error("Error processing request.", e);

			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Unexpected error");
		}
	}
}
