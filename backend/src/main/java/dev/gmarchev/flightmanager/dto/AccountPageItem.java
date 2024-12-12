package dev.gmarchev.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AccountPageItem {

	private String username;

	private String email;

	private String firstName;

	private String lastName;
}
