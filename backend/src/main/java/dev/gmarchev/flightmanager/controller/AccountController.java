package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.AccountPageItem;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.AccountCreateRequest;
import dev.gmarchev.flightmanager.service.AccountService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee-accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	private final AccountService accountService;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody @Valid AccountCreateRequest accountCreateRequest) {

		accountService.createEmployeeAccount(accountCreateRequest);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Account created successfully");
	}

	@GetMapping
	public ResponseEntity<PageResponse<AccountPageItem>> getAccounts(
			@RequestParam(required = false) @Nullable String username,
			@RequestParam(required = false) @Nullable String email,
			@RequestParam(required = false) @Nullable String firstName,
			@RequestParam(required = false) @Nullable String lastName,
			@RequestParam int page,
			@RequestParam int size) {

		return ResponseEntity.ok(accountService.getEmployeeAccounts(username, email, firstName, lastName, page, size));
	}
}
