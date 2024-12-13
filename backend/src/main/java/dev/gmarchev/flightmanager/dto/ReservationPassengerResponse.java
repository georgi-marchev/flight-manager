package dev.gmarchev.flightmanager.dto;

import dev.gmarchev.flightmanager.model.SeatType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationPassengerResponse {

	private String firstName;

	private String middleName;

	private String lastName;

	private String personalIdentificationNumber;

	private String phoneNumber;

	private String nationality;

	private SeatType seatType;
}
