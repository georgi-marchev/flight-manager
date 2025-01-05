package dev.gmarchev.flightmanager.controller;

import java.util.List;

import dev.gmarchev.flightmanager.dto.PilotCreateRequest;
import dev.gmarchev.flightmanager.dto.PilotResponse;
import dev.gmarchev.flightmanager.service.PilotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pilots")
@RequiredArgsConstructor
public class PilotController {

	private final PilotService pilotService;

	@GetMapping
	public ResponseEntity<List<PilotResponse>> getPilots() {

		return ResponseEntity.ok(pilotService.getPilots());
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody @Valid PilotCreateRequest pilotCreateRequest) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new Object() {
					public final Long pilotId = pilotService.createPilot(pilotCreateRequest);
				});
	}
}
