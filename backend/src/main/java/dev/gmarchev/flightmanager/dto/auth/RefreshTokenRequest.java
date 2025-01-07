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
public class RefreshTokenRequest {

//	@NotBlank(message = "Refresh token cannot be empty or blank")
	@NotBlank(message = "refreshToken поле е задължително.")
	private String refreshToken;
}
