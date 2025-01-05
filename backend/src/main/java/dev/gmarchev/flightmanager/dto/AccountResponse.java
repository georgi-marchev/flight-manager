package dev.gmarchev.flightmanager.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountResponse {

	private Long id;

	private String username;

	private String email;

	private String firstName;

	private String lastName;

	private String personalIdentificationNumber;

	private String address;

	private String phoneNumber;

	private Set<Long> roles;
}
