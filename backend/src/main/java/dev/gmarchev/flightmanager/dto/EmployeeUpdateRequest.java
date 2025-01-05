package dev.gmarchev.flightmanager.dto;

import dev.gmarchev.flightmanager.validation.phonenumber.ValidNotEmptyPhoneNumber;
import dev.gmarchev.flightmanager.validation.pin.ValidNotEmptyEgnOrForeignerPin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeUpdateRequest {

	private String newPassword;

	@NotEmpty(message = "Имейл е задължителен.")
	@Email(message = "Невалиден формат на имейл.")
	private String email;

	@NotBlank(message = "Име е задължително.")
	private String firstName;

	@NotBlank(message = "Фамилия е задължителна.")
	private String lastName;

	@ValidNotEmptyEgnOrForeignerPin(message = "Невалидно ЕГН или ЛНЧ.")
	private String personalIdentificationNumber;

	@NotBlank(message = "Адрес е задължителен")
	private String address;

	@ValidNotEmptyPhoneNumber(
			message = "Валиден телефонен номер може да съдържа само цифри, интервали и може да започва с \"+\".")
	private String phoneNumber;
}
