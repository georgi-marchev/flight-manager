package dev.gmarchev.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PilotResponse {

	private Long id;

	private String firstName;

	private String lastName;
}
