package dev.gmarchev.flightmanager.controller;

import java.util.Optional;

import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.AccountRequest;
import dev.gmarchev.flightmanager.service.AccountService;
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
@RequestMapping("/employee-accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	private final AccountService accountService;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody AccountRequest accountRequest) {

		accountService.createEmployeeAccount(accountRequest);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Account created successfully");
	}

	@GetMapping
	public ResponseEntity<PageResponse> getAccounts(
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName,
			@RequestParam int page,
			@RequestParam int size) {

		return ResponseEntity.ok(accountService.getEmployeeAccounts(
				Optional.ofNullable(username),
				Optional.ofNullable(email),
				Optional.ofNullable(firstName),
				Optional.ofNullable(lastName),
				page,
				size));
	}
}
