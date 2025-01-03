package dev.gmarchev.flightmanager.dto;

import java.util.List;

import jakarta.validation.Valid;
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

	@Min(value = 1, message = "Невалиден идентификатор на полет.")
	private long flightId;

	@NotEmpty(message = "Имейл е задължителен.")
	@Email(message = "Невалиден формат на имейл.")
	private String contactEmail;

	@NotEmpty(message = "Списък с пътници е задължителен.")
	@Valid
	private List<ReservationRequestPassenger> passengers;
}


