package dev.gmarchev.flightmanager.dto.auth;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

	private String accessToken;

	private String refreshToken;

	private Collection<String> authorities;
}
