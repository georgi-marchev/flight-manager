package dev.gmarchev.flightmanager.service;

import java.util.Optional;

import dev.gmarchev.flightmanager.model.Role;
import dev.gmarchev.flightmanager.model.RoleType;
import dev.gmarchev.flightmanager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

	private final RoleRepository roleRepository;

	public Optional<Role> getAdminRole() {

		return getRole(RoleType.ADMIN);
	}

	private Optional<Role> getRole(RoleType roleType) {

		return roleRepository.findByName(roleType.name());
	}

	public Optional<Role> getEmployeeRole() {

		return getRole(RoleType.EMPLOYEE);
	}

	public Role createAdminRole() {

		return createRole(RoleType.ADMIN);
	}

	private Role createRole(RoleType roleType) {

		Role role = Role.builder()
				.name(roleType.name())
				.build();

		roleRepository.save(role);

		log.info("Role created: {}", role);

		return role;
	}

	public Role createEmployeeRole() {

		return createRole(RoleType.EMPLOYEE);
	}
}
