package dev.gmarchev.flightmanager.dto.flight;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FlightPageItem {

	private Long id;

	private ZonedDateTime departureTime;

	private ZonedDateTime arrivalTime;

	private String departureLocation;

	private String destinationLocation;
}
