package dev.gmarchev.flightmanager.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationRequest {

	@Min(1)
	private long flightId;

	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Email is not valid")
	private String contactEmail;

	@NotEmpty
	private List<ReservationRequestPassenger> passengers;
}


