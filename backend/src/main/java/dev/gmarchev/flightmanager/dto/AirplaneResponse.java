package dev.gmarchev.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AirplaneResponse {

	private final Long id;

	private final String serialNumber;

	private String modelNumber;

	private int capacityEconomy;

	private int capacityBusiness;
}
