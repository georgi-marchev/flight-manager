package dev.gmarchev.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReservationPageItem {

	private Long id;

	private Long reservationFlight;

	private String contactEmail;
}
