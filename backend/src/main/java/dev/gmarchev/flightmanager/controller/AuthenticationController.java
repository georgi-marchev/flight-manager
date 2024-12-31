package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.AuthenticationRequest;
import dev.gmarchev.flightmanager.dto.AuthenticationResponse;
import dev.gmarchev.flightmanager.dto.RefreshTokenRequest;
import dev.gmarchev.flightmanager.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
/**
 * TODO: Improve by adding access and refresh tokens to the database
 * When new tokens are issued on login or refresh, invalidate old tokens.
 */
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
	public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {

		try	{

			String username = jwtService.extractUsername(refreshTokenRequest.getRefreshToken());

			return ResponseEntity.ok(new AuthenticationResponse(
					jwtService.generateAccessToken(username), jwtService.generateRefreshToken(username)));

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
