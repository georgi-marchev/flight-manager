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
public class PilotCreateRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;
}
