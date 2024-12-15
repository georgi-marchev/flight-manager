package dev.gmarchev.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PassengerPageItem {

	private Long reservationId;

	private String firstName;

	private String middleName;

	private String lastName;
}
