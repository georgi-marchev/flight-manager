package dev.gmarchev.flightmanager.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userName;

	private String password;

	private String email;

	private String firstName;

	private String lastName;

	private String personalIdentificationNumber;

	private String address;

	private String phoneNumber;

	@ManyToMany
	@JoinTable(
			name = "account_roles",
			joinColumns = @JoinColumn(name = "account_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Override
	public String toString() {

		return "Account{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", personalIdentificationNumber='" + personalIdentificationNumber + '\'' +
				", address='" + address + '\'' +
				", roles=" + roles +
				'}';
	}
}
