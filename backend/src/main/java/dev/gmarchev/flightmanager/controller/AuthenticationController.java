package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.AuthenticationRequest;
import dev.gmarchev.flightmanager.dto.AuthenticationResponse;
import dev.gmarchev.flightmanager.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	@PostMapping({"/login", "/login/"})
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {

		String username = authenticationRequest.getUsername();

		// Perform authentication
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				username, authenticationRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		if (authentication.isAuthenticated()) {

			SecurityContextHolder.getContext().setAuthentication(authentication);

			return ResponseEntity.ok(
					new AuthenticationResponse(
							jwtService.generateAccessToken(username),
							jwtService.generateRefreshToken(username)));

		} else {

			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid credentials");
		}
	}

	@PostMapping({"/refresh-token", "/refresh-token/"})
	public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {

		if (refreshToken == null || refreshToken.isEmpty()) {

			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Refresh token is required");
		}

		if (jwtService.isTokenExpired(refreshToken)) {

			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Refresh token has expired");
		}

		String username = jwtService.extractUsername(refreshToken);

		// TODO: Improve by adding refresh tokens to the database and check if it is present and is not blacklisted.
		//  Blacklist old refresh token if new one is issued.
		return ResponseEntity.ok(new AuthenticationResponse(
				jwtService.generateAccessToken(username), jwtService.generateRefreshToken(username)));
	}
}
