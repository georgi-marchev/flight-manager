package dev.gmarchev.flightmanager.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FlightPageItem {

	private ZonedDateTime departureTime;

	private ZonedDateTime arrivalTime;

	private String departureLocation;

	private String destinationLocation;
}
