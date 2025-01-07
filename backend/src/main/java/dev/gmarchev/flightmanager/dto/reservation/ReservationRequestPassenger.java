package dev.gmarchev.flightmanager.dto.reservation;

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

//	@NotBlank(message = "First name cannot be empty or blank")
	@NotBlank(message = "Име е задължително.")
	private String firstName;

//	@NotBlank(message = "Middle name cannot be empty or blank")
	@NotBlank(message = "Презиме е задължително.")
	private String middleName;

//	@NotBlank(message = "Last name cannot be empty or blank")
	@NotBlank(message = "Фамилия е задължителна.")
	private String lastName;

	@ValidNotEmptyEgnOrForeignerPin(message = "Невалидно ЕГН или ЛНЧ.")
	private String personalIdentificationNumber;

	@ValidNotEmptyPhoneNumber(
			message = "Валиден телефонен номер може да съдържа само цифри, интервали и може да започва с \"+\".")
	private String phoneNumber;

	@NotBlank(message = "Националност е задължителна.")
	private String nationality;

	@NotNull(message = "Вид на място е задължително.")
	private SeatType seatType;
}
