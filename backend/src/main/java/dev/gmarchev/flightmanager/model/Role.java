package dev.gmarchev.flightmanager.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private RoleType name;

	@ManyToMany(mappedBy = "roles")
	private Set<Account> accounts;

	@Override
	public boolean equals(Object o) {

		if (o == null || getClass() != o.getClass())
			return false;
		Role role = (Role) o;
		return Objects.equals(name, role.name);
	}

	@Override
	public int hashCode() {

		return Objects.hashCode(name);
	}
}
