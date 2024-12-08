package dev.gmarchev.flightmanager.service;

import dev.gmarchev.flightmanager.model.Role;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StartUpService {

	private final RoleService roleService;

	private final AccountService accountService;

	@PostConstruct
	@Transactional
	private void initializeData() {

		log.info("Running initialization sequence...");

		Role administratorRole = roleService.getAdminRole()
				.orElseGet(roleService::createAdminRole);

		roleService.getEmployeeRole()
				.orElseGet(roleService::createEmployeeRole);

		accountService.getDefaultAdminAccount(administratorRole)
				.orElseGet(() -> accountService.createDefaultAdminAccount(administratorRole));

		log.info("Initialization sequence completed.");
	}
}
