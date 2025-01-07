package dev.gmarchev.flightmanager.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dev.gmarchev.flightmanager.dto.account.AccountPageItem;
import dev.gmarchev.flightmanager.dto.account.AccountResponse;
import dev.gmarchev.flightmanager.dto.account.EmployeeUpdateRequest;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.account.AccountCreateRequest;
import dev.gmarchev.flightmanager.exceptions.EntityNotFoundException;
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
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Role with name %s was not found in the database", roleType),
						"Роля не може да бъде намерена."));

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

		Page<Account> page = accountRepository.findAccountByOptionalFilters(
				username, email, firstName, lastName, RoleType.EMPLOYEE, PageRequest.of(pageNumber, pageSize));

		List<AccountPageItem> accounts = page.get()
				.map(a -> AccountPageItem.builder()
						.id(a.getId())
						.username(a.getUsername())
						.email(a.getEmail())
						.firstName(a.getFirstName())
						.lastName(a.getLastName())
						.build())
				.collect(Collectors.toUnmodifiableList());

		return new PageResponse<>(accounts, page.hasNext());
	}

	public AccountResponse getEmployeeAccountById(Long accountId) {

		Account account = findEmployeeAccountById(accountId);

		return new AccountResponse(
				account.getId(),
				account.getUsername(),
				account.getEmail(),
				account.getFirstName(),
				account.getLastName(),
				account.getPersonalIdentificationNumber(),
				account.getAddress(),
				account.getPhoneNumber(),
				account.getRoles().stream().map(Role::getId).collect(Collectors.toSet())
		);
	}

	private Account findEmployeeAccountById(long accountId) {

		return accountRepository.findByIdAndRolesName(accountId, RoleType.EMPLOYEE)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Account with ID %d cannot be found", accountId),
						"Профил на служител не може да бъде открит."));
	}

	public void updateEmployeeAccount(Long accountId, EmployeeUpdateRequest employeeUpdateRequest) {

		Account account = findEmployeeAccountById(accountId);

		if (employeeUpdateRequest.getNewPassword() != null && !employeeUpdateRequest.getNewPassword().isBlank()) {

			account.setPassword(passwordEncoder.encode(employeeUpdateRequest.getNewPassword()));
		}
		account.setEmail(employeeUpdateRequest.getEmail());
		account.setFirstName(employeeUpdateRequest.getFirstName());
		account.setLastName(employeeUpdateRequest.getLastName());
		account.setPersonalIdentificationNumber(employeeUpdateRequest.getPersonalIdentificationNumber());
		account.setAddress(employeeUpdateRequest.getAddress());
		account.setPhoneNumber(employeeUpdateRequest.getPhoneNumber());

		accountRepository.save(account);
	}
}
