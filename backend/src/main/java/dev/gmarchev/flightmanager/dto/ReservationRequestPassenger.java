package dev.gmarchev.flightmanager.dto;

import dev.gmarchev.flightmanager.model.SeatType;
import dev.gmarchev.flightmanager.validation.phonenumber.ValidNotEmptyPhoneNumber;
import dev.gmarchev.flightmanager.validation.pin.ValidNotEmptyEgnOrForeignerPin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ReservationRequestPassenger {

	@NotBlank(message = "First name cannot be empty or blank")
	private String firstName;

	@NotBlank(message = "Middle name cannot be empty or blank")
	private String middleName;

	// TODO: Will not work for foreigners without middle name
	@NotBlank(message = "Last name cannot be empty or blank")
	private String lastName;

	@ValidNotEmptyEgnOrForeignerPin
	private String personalIdentificationNumber;

	@ValidNotEmptyPhoneNumber
	private String phoneNumber;

	@NotBlank
	private String nationality;

	@NotNull
	private SeatType seatType;
}
