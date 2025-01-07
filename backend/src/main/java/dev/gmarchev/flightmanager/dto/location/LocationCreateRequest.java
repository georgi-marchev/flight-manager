package dev.gmarchev.flightmanager.dto.location;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationCreateRequest {

	@NotBlank(message = "Име на летище е задължително.")
	private String airportName;

	@NotBlank(message = "Име на град е задължително.")
	private String city;

	@NotBlank(message = "Име на държава е задължително.")
	private String country;
}
