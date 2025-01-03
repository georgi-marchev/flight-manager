package dev.gmarchev.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationResponse {

	private Long id;

	private String airportName;

	private String city;

	private String country;
}
