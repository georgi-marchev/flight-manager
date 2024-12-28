package dev.gmarchev.flightmanager.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FlightResponse {

	private ZonedDateTime departureTime;

	private ZonedDateTime arrivalTime;

	private int availableSeatsEconomy;

	private int availableSeatsBusiness;

	private String departureLocation;

	private String destinationLocation;
}
