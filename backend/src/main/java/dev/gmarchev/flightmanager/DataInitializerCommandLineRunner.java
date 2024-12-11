package dev.gmarchev.flightmanager;

import dev.gmarchev.flightmanager.model.Role;
import dev.gmarchev.flightmanager.service.AccountService;
import dev.gmarchev.flightmanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializerCommandLineRunner implements CommandLineRunner {

	private final RoleService roleService;

	private final AccountService accountService;

	@Override
	public void run(String... args) throws Exception {

		log.info("Running data initialization sequence...");

		Role administratorRole = roleService.getAdminRole()
				.orElseGet(roleService::createAdminRole);

		roleService.getEmployeeRole()
				.orElseGet(roleService::createEmployeeRole);

		accountService.getDefaultAdminAccount(administratorRole)
				.orElseGet(() -> accountService.createDefaultAdminAccount(administratorRole));

		log.info("Data initialization completed.");
	}
}
