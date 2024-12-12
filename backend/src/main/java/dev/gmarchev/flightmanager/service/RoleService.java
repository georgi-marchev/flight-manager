package dev.gmarchev.flightmanager.service;

import java.util.List;
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

	public Optional<Role> getRole(RoleType roleType) {

		return roleRepository.findByName(roleType);
	}

	public void createRoles(List<RoleType> roleTypes) {

		List<Role> roles = roleTypes.stream()
				.map(r -> Role.builder().name(r).build())
				.toList();

		roleRepository.saveAll(roles);

		log.info("Roles created: {}", roles);
	}
}
