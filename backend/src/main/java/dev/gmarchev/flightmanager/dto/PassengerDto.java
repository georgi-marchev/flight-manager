package dev.gmarchev.flightmanager.dto;

import dev.gmarchev.flightmanager.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PassengerDto {

	private String firstName;

	private String middleName;

	private String lastName;

	private String personalIdentificationNumber;

	private String phoneNumber;

	private String nationality;

	private SeatType seatType;
}
