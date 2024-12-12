package dev.gmarchev.flightmanager;

import java.util.ArrayList;
import java.util.List;

import dev.gmarchev.flightmanager.config.AccountMapping;
import dev.gmarchev.flightmanager.model.RoleType;
import dev.gmarchev.flightmanager.service.AccountService;
import dev.gmarchev.flightmanager.service.RoleService;
import jakarta.transaction.Transactional;
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

	private final AccountMapping accountMapping;

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		log.info("Running data initialization sequence...");

		createMissingRoles();

		createMissingAdminAccount();

		log.info("Data initialization completed.");
	}

	public void createMissingRoles() {

		List<RoleType> rolesToCreate = new ArrayList<>();

		for (RoleType roleType : RoleType.values()) {

			if (!roleService.getRole(roleType).isPresent()) {

				rolesToCreate.add(roleType);
			}
		}

		if (!rolesToCreate.isEmpty()) {

			roleService.createRoles(rolesToCreate);
		}
	}

	public void createMissingAdminAccount() {

		if (!accountService.accountForRoleExists(RoleType.ADMIN)) {

			accountService.createAccount(accountMapping.admin(), RoleType.ADMIN);
		}
	}
}
