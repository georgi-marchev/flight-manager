package dev.gmarchev.flightmanager.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationRequest {

	private Long flightId;

	private String contactEmail;

	private List<PassengerDto> passengers;
}


