package dev.gmarchev.flightmanager.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationResponse {

	private String contactEmail;

	private Long reservationFlight;

	private List<ReservationPassengerResponse> passengers;
}
