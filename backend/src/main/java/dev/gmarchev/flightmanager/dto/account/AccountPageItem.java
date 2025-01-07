package dev.gmarchev.flightmanager.dto.account;

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

	private Long id;

	private String username;

	private String email;

	private String firstName;

	private String lastName;
}
