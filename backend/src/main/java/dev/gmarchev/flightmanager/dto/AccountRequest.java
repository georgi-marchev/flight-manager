package dev.gmarchev.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
// TODO: Validation
public class AccountRequest {

	private String userName;

	private String password;

	private String email;

	private String firstName;

	private String lastName;

	private String personalIdentificationNumber;

	private String address;

	private String phoneNumber;

	@Override
	public String toString() {

		return "AccountRequest{" +
				"userName='" + userName + '\'' +
				", email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", personalIdentificationNumber='" + personalIdentificationNumber + '\'' +
				", address='" + address + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}
}
