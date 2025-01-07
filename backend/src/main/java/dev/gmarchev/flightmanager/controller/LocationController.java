package dev.gmarchev.flightmanager.controller;

import java.util.List;

import dev.gmarchev.flightmanager.dto.location.LocationCreateRequest;
import dev.gmarchev.flightmanager.dto.location.LocationResponse;
import dev.gmarchev.flightmanager.service.LocationService;
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
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

	private final LocationService locationService;

	@GetMapping
	public ResponseEntity<List<LocationResponse>> getLocations() {

		return ResponseEntity.ok(locationService.getLocations());
	}

	@PostMapping
	public ResponseEntity<Object> createLocation(@RequestBody @Valid LocationCreateRequest locationCreateRequest) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new Object() {
					public final Long locationId = locationService.createLocation(locationCreateRequest);
				});
	}
}
