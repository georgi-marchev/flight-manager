package dev.gmarchev.flightmanager.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String personalIdentificationNumber;

	private String address;

	private String phoneNumber;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "account_roles",
			joinColumns = @JoinColumn(name = "account_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Override
	public boolean equals(Object o) {

		if (o == null || getClass() != o.getClass())
			return false;
		Account account = (Account) o;
		return Objects.equals(userName, account.userName);
	}

	@Override
	public int hashCode() {

		return Objects.hashCode(userName);
	}

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
