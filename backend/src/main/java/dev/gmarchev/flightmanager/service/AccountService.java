package dev.gmarchev.flightmanager.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import dev.gmarchev.flightmanager.dto.AccountPageItem;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.AccountRequest;
import dev.gmarchev.flightmanager.model.Account;
import dev.gmarchev.flightmanager.model.Role;
import dev.gmarchev.flightmanager.model.RoleType;
import dev.gmarchev.flightmanager.repository.AccountRepository;
import dev.gmarchev.flightmanager.repository.RoleRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

	public Account createAccount(AccountRequest accountRequest, RoleType roleType) {

		Role role = roleRepository.findByName(roleType)
				.orElseThrow(() -> new IllegalStateException(String.format("Role %s is missing")));

		Account account = Account.builder()
				.username(accountRequest.getUserName())
				.password(passwordEncoder.encode(accountRequest.getPassword()))
				.email(accountRequest.getEmail())
				.firstName(accountRequest.getFirstName())
				.lastName(accountRequest.getLastName())
				.personalIdentificationNumber(accountRequest.getPersonalIdentificationNumber())
				.address(accountRequest.getAddress())
				.phoneNumber(accountRequest.getPhoneNumber())
				.roles(Set.of(role))
				.build();

		accountRepository.save(account);

		log.info("Account created: {}", account);

		return account;
	}

	public void createEmployeeAccount(AccountRequest accountRequest) {

		createAccount(accountRequest, RoleType.EMPLOYEE);
	}

	public PageResponse<AccountPageItem> getEmployeeAccounts(
			Optional<String> username,
			Optional<String> email,
			Optional<String> firstName,
			Optional<String> lastName,
			int pageNumber,
			int pageSize) {
		// Creating dynamic Specification for Account
		Specification<Account> spec = (account, query, criteriaBuilder) -> {

			// TODO: Replace strings with constants.
			Predicate predicate = criteriaBuilder.equal(account.join("roles").get("name"), RoleType.EMPLOYEE.name());

			Map<String, Optional<String>> filters = Map.of(
					"username", username, "email", email, "firstName", firstName, "lastName", lastName);

			for (Entry<String, Optional<String>> keyToVal : filters.entrySet()) {

				Optional<String> value = keyToVal.getValue();

				if (value.isPresent()) {

					predicate = criteriaBuilder.and(
							predicate,
							criteriaBuilder.like(
									criteriaBuilder.lower(account.get(keyToVal.getKey())),
									"%" + value.get().toLowerCase() + "%"));
				}
			}

			return predicate;
		};

		Page<Account> page = accountRepository.findAll(spec, PageRequest.of(pageNumber, pageSize));

		List<AccountPageItem> accounts = page.get()
				.map(a -> AccountPageItem.builder()
						.username(a.getUsername())
						.email(a.getEmail())
						.firstName(a.getFirstName())
						.lastName(a.getLastName())
						.build())
				.collect(Collectors.toUnmodifiableList());
		page.hasNext();

		return new PageResponse<>(accounts, page.hasNext());
	}
}
