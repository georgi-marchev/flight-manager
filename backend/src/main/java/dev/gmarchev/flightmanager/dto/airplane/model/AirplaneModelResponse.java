package dev.gmarchev.flightmanager.dto.airplane.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AirplaneModelResponse {

	private final Long id;

	private final String modelNumber;

	private final int capacityEconomy;

	private final int capacityBusiness;
}
