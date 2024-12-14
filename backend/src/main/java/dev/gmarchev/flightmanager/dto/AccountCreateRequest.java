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

	@NotBlank(message = "Username cannot be empty or blank")
	private String username;

	@NotBlank(message = "Password cannot be empty or blank")
	private String password;

	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Email is not valid")
	private String email;

	@NotBlank(message = "First name cannot be empty or blank")
	private String firstName;

	@NotBlank(message = "Last name cannot be empty or blank")
	private String lastName;

	@ValidNotEmptyEgnOrForeignerPin
	private String personalIdentificationNumber;

	@NotBlank(message = "Address cannot be empty or blank")
	private String address;

	@ValidNotEmptyPhoneNumber
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
