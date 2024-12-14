package dev.gmarchev.flightmanager.dto;

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

	@Min(1)
	private long flightDepartureLocation;

	@Min(1)
	private long flightDestinationLocation;

	@Min(1)
	private long flightAirplane;

	@Min(1)
	private long flightPilot;
}
