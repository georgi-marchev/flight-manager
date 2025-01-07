package dev.gmarchev.flightmanager.dto.pilot;

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

	@NotBlank(message = "Име е задължително.")
	private String firstName;

	@NotBlank(message = "Фамилия е задължителна.")
	private String lastName;
}
