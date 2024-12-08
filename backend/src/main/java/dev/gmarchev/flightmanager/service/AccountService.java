package dev.gmarchev.flightmanager.service;

import java.util.Optional;
import java.util.Set;

import dev.gmarchev.flightmanager.dto.AccountRequest;
import dev.gmarchev.flightmanager.model.Account;
import dev.gmarchev.flightmanager.model.Role;
import dev.gmarchev.flightmanager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;

	private final PasswordEncoder passwordEncoder;

	public Optional<Account> getDefaultAdminAccount(Role administratorRole) {

		return accountRepository.findByRoles(administratorRole)
				.stream()
				.findFirst();
	}

	public Account createDefaultAdminAccount(Role administratorRole) {

		// TODO: Add Account data as configuration
		AccountRequest accountRequest = AccountRequest.builder()
				.userName("admin")
				.password("passs")
				.email("fake@fake.com")
				.firstName("John")
				.lastName("Doe")
				.personalIdentificationNumber("1111111111")
				.address("Some fake address")
				.build();

		return createAccount(accountRequest, administratorRole);
	}

	private Account createAccount(AccountRequest accountRequest, Role role) {

		Account account = Account.builder()
				.userName(accountRequest.getUserName())
				.password(passwordEncoder.encode(accountRequest.getPassword()))
				.email(accountRequest.getEmail())
				.firstName(accountRequest.getFirstName())
				.lastName(accountRequest.getLastName())
				.personalIdentificationNumber(accountRequest.getPersonalIdentificationNumber())
				.address(accountRequest.getAddress())
				.roles(Set.of(role))
				.build();

		accountRepository.save(account);

		log.info("Account created: {}", account);

		return account;
	}
}
