package dev.gmarchev.flightmanager.dto.auth;

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

//	@NotBlank(message = "Username cannot be empty or blank")
	@NotBlank(message = "Потребителско име е задължително.")
	private String username;

//	@NotBlank(message = "Password cannot be empty or blank")
	@NotBlank(message = "Парола е задължителна.")
	private String password;
}
