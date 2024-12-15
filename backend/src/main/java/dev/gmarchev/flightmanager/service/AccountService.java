package dev.gmarchev.flightmanager.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dev.gmarchev.flightmanager.dto.AccountPageItem;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.AccountCreateRequest;
import dev.gmarchev.flightmanager.model.Account;
import dev.gmarchev.flightmanager.model.Role;
import dev.gmarchev.flightmanager.model.RoleType;
import dev.gmarchev.flightmanager.repository.AccountRepository;
import dev.gmarchev.flightmanager.repository.RoleRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	public boolean accountForRoleExists(RoleType roleType) {

		return accountRepository.findFirstByRolesName(roleType).isPresent();
	}

	public Account createAccount(AccountCreateRequest accountCreateRequest, RoleType roleType) {

		Role role = roleRepository.findByName(roleType)
				.orElseThrow(() -> new IllegalStateException(String.format("Role %s is missing")));

		Account account = Account.builder()
				.username(accountCreateRequest.getUsername())
				.password(passwordEncoder.encode(accountCreateRequest.getPassword()))
				.email(accountCreateRequest.getEmail())
				.firstName(accountCreateRequest.getFirstName())
				.lastName(accountCreateRequest.getLastName())
				.personalIdentificationNumber(accountCreateRequest.getPersonalIdentificationNumber())
				.address(accountCreateRequest.getAddress())
				.phoneNumber(accountCreateRequest.getPhoneNumber())
				.roles(Set.of(role))
				.build();

		accountRepository.save(account);

		log.info("Account created: {}", account);

		return account;
	}

	public void createEmployeeAccount(AccountCreateRequest accountCreateRequest) {

		createAccount(accountCreateRequest, RoleType.EMPLOYEE);
	}

	public PageResponse<AccountPageItem> getEmployeeAccounts(
			@Nullable String username,
			@Nullable String email,
			@Nullable String firstName,
			@Nullable String lastName,
			int pageNumber,
			int pageSize) {

		Page<Account> page = accountRepository.findReservationByOptionalFilters(
				username, email, firstName, lastName, RoleType.EMPLOYEE, PageRequest.of(pageNumber, pageSize));

		List<AccountPageItem> accounts = page.get()
				.map(a -> AccountPageItem.builder()
						.username(a.getUsername())
						.email(a.getEmail())
						.firstName(a.getFirstName())
						.lastName(a.getLastName())
						.build())
				.collect(Collectors.toUnmodifiableList());

		return new PageResponse<>(accounts, page.hasNext());
	}
}
