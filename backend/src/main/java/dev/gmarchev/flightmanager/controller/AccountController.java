package dev.gmarchev.flightmanager.controller;

import java.util.Set;

import dev.gmarchev.flightmanager.model.Role;
import dev.gmarchev.flightmanager.model.RoleType;
import dev.gmarchev.flightmanager.model.Account;
import dev.gmarchev.flightmanager.repository.RoleRepository;
import dev.gmarchev.flightmanager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountRepository accountRepository;

	private final RoleRepository roleRepository;

	@PostMapping(value = "/create")
	public ResponseEntity<String> create() {

		Role admin = Role.builder().name(RoleType.ADMIN.name()).build();

		roleRepository.save(admin);

		Role role = Role.builder().name(RoleType.EMPLOYEE.name()).build();

		roleRepository.save(role);

		Account account = Account.builder().roles(Set.of(role)).build();

		accountRepository.save(account);

		return ResponseEntity.ok(admin.getId() + "-" + role.getId() + "-" + account.getId());
	}
}
