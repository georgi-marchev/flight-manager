package dev.gmarchev.flightmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

	@NotBlank(message = "Username cannot be empty or blank")
	private String username;

	@NotBlank(message = "Password cannot be empty or blank")
	private String password;
}
