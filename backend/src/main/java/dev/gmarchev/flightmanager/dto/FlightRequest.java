package dev.gmarchev.flightmanager.dto;

import java.time.ZonedDateTime;

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
public class FlightRequest {

	private ZonedDateTime departureTime;

	private ZonedDateTime arrivalTime;

	private Long flightDepartureLocation;

	private Long flightDestinationLocation;

	private Long flightAirplane;

	private Long flightPilot;
}
