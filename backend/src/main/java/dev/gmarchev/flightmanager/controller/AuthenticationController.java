package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.AuthenticationRequest;
import dev.gmarchev.flightmanager.dto.RefreshTokenRequest;
import dev.gmarchev.flightmanager.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

		return ResponseEntity.ok(
				authenticationService.getAuthenticationResponse(authenticationRequest));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {

		return ResponseEntity.ok(authenticationService.getAuthenticationResponse(refreshTokenRequest));
	}
}
