package dev.gmarchev.flightmanager.dto;

import dev.gmarchev.flightmanager.validation.phonenumber.ValidNotEmptyPhoneNumber;
import dev.gmarchev.flightmanager.validation.pin.ValidNotEmptyEgnOrForeignerPin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountCreateRequest {

//	@NotBlank(message = "Username cannot be empty or blank")
	@NotBlank(message = "Потребителско име е задължително.")
	private String username;

//	@NotBlank(message = "Password cannot be empty or blank")
	@NotBlank(message = "Парола е задължителна.")
	private String password;

//	@NotEmpty(message = "Email cannot be empty")
//	@Email(message = "Email is not valid")
	@NotEmpty(message = "Имейл е задължителен.")
	@Email(message = "Невалиден формат на имейл.")
	private String email;

//	@NotBlank(message = "First name cannot be empty or blank")
	@NotBlank(message = "Име е задължително.")
	private String firstName;

//	@NotBlank(message = "Last name cannot be empty or blank")
	@NotBlank(message = "Фамилия е задължителна.")
	private String lastName;

	@ValidNotEmptyEgnOrForeignerPin(message = "Невалидно ЕГН или ЛНЧ.")
	private String personalIdentificationNumber;

	// @NotBlank(message = "Address cannot be empty or blank")
	@NotBlank(message = "Адрес е задължителен")
	private String address;

	@ValidNotEmptyPhoneNumber(
			message = "Валиден телефонен номер може да съдържа само цифри, интервали и може да започва с \"+\".")
	private String phoneNumber;

	@Override
	public String toString() {

		return "AccountRequest{" +
				"username='" + username + '\'' +
				", email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", personalIdentificationNumber='" + personalIdentificationNumber + '\'' +
				", address='" + address + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}
}
