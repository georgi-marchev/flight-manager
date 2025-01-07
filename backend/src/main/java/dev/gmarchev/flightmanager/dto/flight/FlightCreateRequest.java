package dev.gmarchev.flightmanager.dto.flight;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightCreateRequest {

	@NotNull
	private ZonedDateTime departureTime;

	@NotNull
	private ZonedDateTime arrivalTime;

	@Min(value = 1, message = "Невалиден идентификатор на локация за излитане.")
	private long flightDepartureLocation;

	@Min(value = 1, message = "Невалиден идентификатор на локация за кацане.")
	private long flightDestinationLocation;

	@Min(value = 1, message = "Невалиден идентификатор на самолет.")
	private long flightAirplane;

	@Min(value = 1, message = "Невалиден идентификатор на пилот.")
	private long flightPilot;
}
