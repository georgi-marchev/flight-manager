package dev.gmarchev.flightmanager.dto;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightUpdateRequest {

	@NotNull
	private ZonedDateTime departureTime;

	@NotNull
	private ZonedDateTime arrivalTime;

	@Min(value = 1, message = "Невалиден идентификатор на пилот.")
	private Long pilotId;
}
