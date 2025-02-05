package dev.gmarchev.flightmanager.service;

import java.util.stream.Collectors;

import dev.gmarchev.flightmanager.dto.auth.AuthenticationRequest;
import dev.gmarchev.flightmanager.dto.auth.AuthenticationResponse;
import dev.gmarchev.flightmanager.dto.auth.RefreshTokenRequest;
import dev.gmarchev.flightmanager.model.Account;
import dev.gmarchev.flightmanager.repository.AccountRepository;
import dev.gmarchev.flightmanager.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;

	private final AccountRepository accountRepository;

	private final JwtService jwtService;

	public AuthenticationResponse getAuthenticationResponse(AuthenticationRequest authenticationRequest) {

		String username = authenticationRequest.getUsername();

		// Perform authentication
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				username, authenticationRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		if (!authentication.isAuthenticated()) {

			throw new BadCredentialsException("User cannot be authenticated");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new AuthenticationResponse(
				jwtService.generateAccessToken(username),
				jwtService.generateRefreshToken(username),
				authentication
						.getAuthorities()
						.stream()
						.map(GrantedAuthority::getAuthority)
						.toList());
	}

	public AuthenticationResponse getAuthenticationResponse(RefreshTokenRequest refreshTokenRequest) {

		String username = jwtService.extractUsername(refreshTokenRequest.getRefreshToken());

		Account account = accountRepository.findByUsername(username)
				.orElseThrow(() -> new BadCredentialsException("Could not load user"));

		return new AuthenticationResponse(
				jwtService.generateAccessToken(username),
				jwtService.generateRefreshToken(username),
				account.getRoles()
						.stream()
						.map(role ->"ROLE_" + role.getName())
						.collect(Collectors.toSet()));
	}
}
