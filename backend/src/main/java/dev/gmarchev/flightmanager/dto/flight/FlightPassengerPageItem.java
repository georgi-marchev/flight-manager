package dev.gmarchev.flightmanager.dto.flight;

import dev.gmarchev.flightmanager.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FlightPassengerPageItem {

	private Long id;

	private Long reservationId;

	private String firstName;

	private String middleName;

	private String lastName;

	private String personalIdentificationNumber;

	private String phoneNumber;

	private String nationality;

	private SeatType seatType;
}
